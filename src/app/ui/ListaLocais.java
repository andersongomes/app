package app.ui;

import java.sql.SQLException;

import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Convert;
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
	private Connection conn;

	public ListaLocais() throws SQLException {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Lugares Cadastrados"), CENTER, TOP + 50);

		conn = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));
		Statement st = conn.createStatement();

		String query = "select * from local";
		ResultSet rs = st.executeQuery(query);

		Rect r = getClientRect();

		String[] gridCaptions = { " ID ", " Nome ", " Estado ", " Cidade " };
		int gridWidths[] = { -5, -35, -30, -30 };
		int gridAligns[] = { LEFT, LEFT, LEFT, LEFT };
		grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
		add(grid, LEFT + 5, TOP + 5, r.width, r.height / 2 + r.height / 3);
		grid.secondStripeColor = Color.getRGB(235, 235, 235);

		String[][] data2 = new String[100][4];
		int i, j;
		i = j = 0;
		try {
			while (rs.next()) {
				int x = (int) rs.getObject("codigo");
				data2[i][j] = (String) Integer.toString(x);
				j++;
				data2[i][j] = (String) rs.getObject("nome");
				j++;
				data2[i][j] = (String) rs.getObject("estado");
				j++;
				data2[i][j] = (String) rs.getObject("cidade");
				j = 0;
				i++;
			}
			grid.setItems(data2);
		} catch (SQLException e) {
			MessageBox.showException(e, true);
		}

		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);

		add(addLocal = new Button("ADICIONAR"), LEFT, SAME, PARENTSIZE + 30, PREFERRED, sp);
		addLocal.setBackColor(Color.GREEN);
		addLocal.setForeColor(Color.WHITE);
		add(editar = new Button("EDITAR"), CENTER, SAME, PARENTSIZE + 30, PREFERRED, sp);
		editar.setBackColor(Color.YELLOW);
		editar.setForeColor(Color.WHITE);
		add(excluir = new Button("EXCLUIR"), RIGHT, SAME, PARENTSIZE + 30, PREFERRED, sp);
		excluir.setBackColor(Color.RED);
		excluir.setForeColor(Color.WHITE);
		/*
		 * Spacer sp2 = new Spacer(0, 0);
		 * 
		 * add(sp2, CENTER, BOTTOM - 400, PARENTSIZE + 10, PREFERRED); add(sair
		 * = new Button("LOGOUT"), LEFT, SAME, PARENTSIZE + 30, PREFERRED, sp2);
		 * sair.setBackColor(Color.RED); sair.setForeColor(Color.WHITE);
		 */

		rs.close();
		st.close();
		conn.close();
	}

	public void onEvent(Event event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == grid) {
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
					Editar ed = new Editar(value[0]);
					ed.popup();
				}
			} else if (event.type == ControlEvent.PRESSED && event.target == excluir) {
				if (grid.getSelectedItem() == null) {
					Toast.show("Selecione pelo menos um item para excluir!", 2000);
					ListaLocais ll;
					try {
						ll = new ListaLocais();
						ll.popup();
					} catch (SQLException e) {
						MessageBox.showException(e, true);
					}
				} else {
					try {
						conn = DriverManager
								.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));
						Statement st = conn.createStatement();
						String value[] = (String[]) grid.getSelectedItem();

						String query = "delete from local where codigo = " + value[0];
						st.execute(query);
						st.close();
						conn.close();
						Toast.show("Local excluído com sucesso!", 2000);
						ListaLocais ll = new ListaLocais();
						ll.popup();
					} catch (SQLException e) {
						MessageBox.showException(e, true);
					}
				}
			}
		}
	}

}
