package app.ui;

import java.sql.SQLException;
import java.util.List;

import app.dao.LocalDAO;
import app.model.Local;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.ListBox;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.gfx.Rect;

public class ListaLocais extends Window {
	Button addLocal, editar, excluir, sair;
	ListBox lb;
	Grid grid;
	LocalDAO localDAO;

	public ListaLocais() throws SQLException {
		super("App Sele��o SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Lugares Cadastrados"), CENTER, TOP + 50);

		localDAO = new LocalDAO();
		List<Local> locais = localDAO.listar();

		Rect r = getClientRect();

		String[] gridCaptions = { " ID ", " Nome ", " Estado ", " Cidade " };
		int gridWidths[] = { -5, -35, -30, -30 };
		int gridAligns[] = { LEFT, LEFT, LEFT, LEFT };
		grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
		add(grid, LEFT + 5, TOP + 5, r.width, r.height / 2 + r.height / 3);
		grid.secondStripeColor = Color.getRGB(235, 235, 235);

		String[][] lista = new String[100][4];
		int i, j;
		i = j = 0;

		for (Local local : locais) {
			lista[i][j] = Integer.toString(local.getCodigo());
			j++;
			lista[i][j] = local.getNome();
			j++;
			lista[i][j] = local.getEstado();
			j++;
			lista[i][j] = local.getCidade();
			j = 0;
			i++;
		}
		grid.setItems(lista);

		Spacer sp = new Spacer(0, 0), sp2 = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);
		add(sp2, CENTER, BOTTOM - 400, PARENTSIZE + 10, PREFERRED);

		add(sair = new Button("Voltar"), LEFT, SAME, PREFERRED+100, 25, sp2);
		sair.setBackColor(Color.RED);
		sair.setForeColor(Color.WHITE);
		
		add(addLocal = new Button("ADICIONAR"), LEFT, SAME, PREFERRED+100, 25, sp);
		addLocal.setBackColor(Color.GREEN);
		addLocal.setForeColor(Color.BLACK);
		add(editar = new Button("EDITAR"), CENTER, SAME, PREFERRED+100, 25, sp);
		editar.setBackColor(Color.YELLOW);
		editar.setForeColor(Color.BLACK);
		add(excluir = new Button("EXCLUIR"), RIGHT, SAME, PREFERRED+100, 25, sp);
		excluir.setBackColor(Color.RED);
		excluir.setForeColor(Color.WHITE);
	}

	public void onEvent(Event event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == grid) {
				@SuppressWarnings(value = "unused")
				Object element = grid.getSelectedItem();
			} else if (event.type == ControlEvent.PRESSED && event.target == addLocal) {
				AddLocal al = new AddLocal();
				al.popup();
			} else if (event.type == ControlEvent.PRESSED && event.target == editar) {
				if (grid.getSelectedItem() == null) {
					Toast.show("Selecione pelo menos um item para editar!", 2000);
					ListaLocais ll;
					try {
						ll = new ListaLocais();
						ll.popup();
					} catch (SQLException e) {
						MessageBox.showException(e, true);
					}
				} else {
					String value[] = (String[]) grid.getSelectedItem();
					EditarLocal ed = new EditarLocal(value[0]);
					ed.popup();
				}
			} else if (event.type == ControlEvent.PRESSED && event.target == excluir) {
				if (grid.getSelectedItem() == null) {
					Toast.show("Selecione pelo menos um local para excluir!", 2000);
					ListaLocais ll;
					try {
						ll = new ListaLocais();
						ll.popup();
					} catch (SQLException e) {
						MessageBox.showException(e, true);
					}
				} else {
					try {
						String value[] = (String[]) grid.getSelectedItem();

						localDAO = new LocalDAO();
						localDAO.excluir(value[0]);

						Toast.show("Local exclu�do com sucesso!", 2000);
						ListaLocais ll = new ListaLocais();
						ll.popup();
					} catch (SQLException e) {
						MessageBox.showException(e, true);
					}
				} 
			} else if (event.type == ControlEvent.PRESSED && event.target == sair) {
				TelaInicial ti = new TelaInicial();
				ti.popup();
			}
		}
	}

}
