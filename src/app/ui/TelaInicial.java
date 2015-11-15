package app.ui;

import java.sql.SQLException;

import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Label;
import totalcross.ui.Spacer;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class TelaInicial extends Window {

	Button listar, addLocal, verificarGps, listarUsuarios, addUsuario;

	public TelaInicial() {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		Spacer sp = new Spacer(0, 0);
		add(new Label("TELA INICIAL DO APP: "), CENTER, AFTER + 50);
		add(sp, CENTER, TOP + 400, PARENTSIZE + 10, PREFERRED);

		add(addLocal = new Button("INSERIR LOCAL"), BEFORE, SAME, PARENTSIZE + 40, 30, sp);
		addLocal.setBackColor(Color.GREEN);
		addLocal.setForeColor(Color.WHITE);

		add(listar = new Button("LISTAR LOCAIS"), AFTER, SAME, PARENTSIZE + 40, 30, sp);
		listar.setBackColor(Color.GREEN);
		listar.setForeColor(Color.WHITE);
		
		add(addUsuario = new Button("INSERIR USUÁRIO"), BEFORE, 150, PARENTSIZE + 40, 30, sp);
		addUsuario.setBackColor(Color.GREEN);
		addUsuario.setForeColor(Color.WHITE);

		add(listarUsuarios = new Button("LISTAR USUARIOS"), AFTER, 150, PARENTSIZE + 40, 30, sp);
		listarUsuarios.setBackColor(Color.GREEN);
		listarUsuarios.setForeColor(Color.WHITE);

	}

	public void onEvent(Event event) {
		if (event.type == ControlEvent.PRESSED && event.target == listar) {
			ListaLocais ll;
			try {
				ll = new ListaLocais();
				ll.popup();
			} catch (SQLException e) {
				MessageBox.showException(e, true);
			}
		} else if (event.type == ControlEvent.PRESSED && event.target == addLocal) {
			AddLocal al = new AddLocal();
			al.popup();
		} else if (event.type == ControlEvent.PRESSED && event.target == verificarGps) {
			GpsView gv = new GpsView();
			gv.popup();
		} else if (event.type == ControlEvent.PRESSED && event.target == addUsuario) {
			AddUsuario au = new AddUsuario();
			au.popup();
		} else if (event.type == ControlEvent.PRESSED && event.target == listarUsuarios) {
			ListaUsuarios ll;
			try {
				ll = new ListaUsuarios();
				ll.popup();
			} catch (SQLException e) {
				MessageBox.showException(e, true);
			}
		}
	}
}
