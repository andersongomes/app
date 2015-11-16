package app.ui;

import app.dao.BaseDAO;
import app.dao.UsuarioDAO;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class App extends MainWindow {

	private Edit usuario, senha;

	private Button btClear, btLogin;

	BaseDAO baseDAO;

	public App() {

		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		setUIStyle(Settings.Android);
		Settings.uiAdjustmentsBasedOnFontHeight = true;
		setBackColor(0xDDDDFF);
	}

	// Initialize the user interface
	@SuppressWarnings("static-access")
	public void initUI() {

		add(new Label("Efetue o seu Login"), CENTER, TOP + 50);

		add(new Label("Usuario: "), LEFT+100, AFTER + 100);
		add(usuario = new Edit(), LEFT, AFTER);
		usuario.setRect(LEFT+100,AFTER+2,FILL-100,30);
		
		add(new Label("Senha: "), LEFT+100, AFTER + 50);
		senha = new Edit();
		senha.setMode(senha.PASSWORD_ALL);
		add(senha, LEFT+100, AFTER);
		senha.setRect(LEFT+100,AFTER+2,FILL-100,30);
		
		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 300, PARENTSIZE + 10, PREFERRED);

		add(btLogin = new Button("Entrar"), BEFORE, SAME, PARENTSIZE + 40, 30, sp);

		add(btClear = new Button("Limpar"), AFTER, SAME, PARENTSIZE + 40, 30, sp);

		btClear.setBackColor(Color.WHITE);

		btLogin.setBackColor(Color.GREEN);
		btLogin.setForeColor(Color.BLACK);

		baseDAO = new BaseDAO();

		baseDAO.criaBaseDeDados();
	}

	public void onEvent(Event e) {
		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == btClear) {
					clear();
				} else if (e.target == btLogin) {
					UsuarioDAO usuarioDAO = new UsuarioDAO();
					if (usuarioDAO.login(usuario.getText(), senha.getText())) {
						TelaInicial ti = new TelaInicial();
						ti.popup();
					} else {
						Toast.show("Usuario e senha não conferem! Tente novamente.", 2000);
					}
				}
				break;
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}

}
