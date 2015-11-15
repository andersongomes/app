package app.ui;

import java.sql.SQLException;
import java.util.List;

import app.dao.UsuarioDAO;
import app.model.Usuario;
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

public class ListaUsuarios extends Window {
	Button addUsuario, editar, excluir, sair;
	ListBox lb;
	Grid grid;
	UsuarioDAO usuarioDAO;

	public ListaUsuarios() throws SQLException {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Usuarios Cadastrados"), CENTER, TOP + 50);

		usuarioDAO = new UsuarioDAO();
		List<Usuario> usuarios = usuarioDAO.listar();

		Rect r = getClientRect();

		String[] gridCaptions = { " ID ", " Nome ", " Usuário " };
		int gridWidths[] = { -5, -55, -40, };
		int gridAligns[] = { LEFT, LEFT, LEFT};
		grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
		add(grid, LEFT + 5, TOP + 5, r.width, r.height / 2 + r.height / 3);
		grid.secondStripeColor = Color.getRGB(235, 235, 235);

		String[][] lista = new String[100][3];
		int i, j;
		i = j = 0;

		for (Usuario usuario : usuarios) {
			lista[i][j] = Integer.toString(usuario.getId());
			j++;
			lista[i][j] = usuario.getNome();
			j++;
			lista[i][j] = usuario.getUsuario();
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
		
		add(addUsuario = new Button("ADICIONAR"), LEFT, SAME, PREFERRED+100, 25, sp);
		addUsuario.setBackColor(Color.GREEN);
		addUsuario.setForeColor(Color.WHITE);
		add(editar = new Button("EDITAR"), CENTER, SAME, PREFERRED+100, 25, sp);
		editar.setBackColor(Color.YELLOW);
		editar.setForeColor(Color.WHITE);
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
			} else if (event.type == ControlEvent.PRESSED && event.target == addUsuario) {
				AddUsuario au = new AddUsuario();
				au.popup();
			} else if (event.type == ControlEvent.PRESSED && event.target == editar) {
				if (grid.getSelectedItem() == null) {
					Toast.show("Selecione pelo menos um item para editar!", 2000);
					ListaUsuarios ll;
					try {
						ll = new ListaUsuarios();
						ll.popup();
					} catch (SQLException e) {
						MessageBox.showException(e, true);
					}
				} else {
					String value[] = (String[]) grid.getSelectedItem();
					EditarUsuario ed = new EditarUsuario(value[0]);
					ed.popup();
				}
			} else if (event.type == ControlEvent.PRESSED && event.target == excluir) {
				if (grid.getSelectedItem() == null) {
					Toast.show("Selecione pelo menos um local para excluir!", 2000);
					ListaUsuarios lu;
					try {
						lu = new ListaUsuarios();
						lu.popup();
					} catch (SQLException e) {
						MessageBox.showException(e, true);
					}
				} else {
					try {
						String value[] = (String[]) grid.getSelectedItem();

						usuarioDAO = new UsuarioDAO();
						usuarioDAO.excluir(value[0]);

						Toast.show("Usuario excluído com sucesso!", 2000);
						ListaUsuarios ll = new ListaUsuarios();
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
