package app.sample;

import java.sql.SQLException;

import totalcross.phone.Dial;
import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.Statement;
import totalcross.sys.Convert;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
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
import totalcross.util.Date;
import totalcross.util.InvalidDateException;

public class App extends MainWindow {

	private Edit edName, edBorn, edPhone, usuario, senha;

	private Button btInsert, btClear, btDial, btTeste;

	private Connection dbcon;

	public App() {

		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		// sets the default user interface style to Android

		// There are others, like

		setUIStyle(Settings.Android);

		// Use font height for adjustments, not pixels

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

	}

	// Initialize the user interface

	public void initUI() {

		add(new Label("Efetue o seu Login"), CENTER, TOP + 50);

		/*
		 * add(new Label("Name: "), LEFT, AFTER + 100); add(edName = new Edit(),
		 * LEFT, AFTER); add(new Label("Born date"), LEFT, AFTER + 50);
		 * add(edBorn = new Edit(), LEFT, AFTER); edBorn.setMode(Edit.DATE);
		 * add(new Label("Phone number"), LEFT, AFTER + 50); // x y w h
		 * add(edPhone = new Edit(), LEFT, AFTER, PARENTSIZE + 70, PREFERRED);
		 * edPhone.setKeyboard(Edit.KBD_NUMERIC); add(btDial = new
		 * Button("Dial"), RIGHT, SAME, PARENTSIZE + 20, PREFERRED);
		 * btDial.setEnabled(Settings.platform.equals(Settings.ANDROID) ||
		 * Settings.isWindowsDevice() || Settings.isIOS());
		 */
		add(new Label("Usuario: "), LEFT, AFTER + 100);
		add(usuario = new Edit(), LEFT, AFTER);

		add(new Label("Senha: "), LEFT, AFTER + 50);
		add(senha = new Edit(), LEFT, AFTER);

		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 300, PARENTSIZE + 10, PREFERRED);

		add(btTeste = new Button("Entrar"), BEFORE, SAME, PARENTSIZE + 40, PREFERRED, sp);

		// add(btInsert = new Button("Insert"), BEFORE, SAME, PARENTSIZE + 40,
		// PREFERRED, sp);

		add(btClear = new Button("Limpar"), AFTER, SAME, PARENTSIZE + 40, PREFERRED, sp);

		// btInsert.setBackColor(Color.GREEN);

		btClear.setBackColor(Color.BLUE);

		btTeste.setBackColor(Color.GREEN);
		/*
		 * if (Settings.onJavaSE || Settings.platform.equals(Settings.WIN32)){
		 * add(new Label("Press F11 on date/number to open keypad"), CENTER,
		 * BOTTOM); }
		 */
		try {

			dbcon = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));

			Statement st = dbcon.createStatement();

			st.execute("create table if not exists person (name varchar, born datetime, number varchar)");
			st.execute(
					"create table if not exists usuario (id_usuario integer primary key autoincrement, nome varchar, uuario varchar, senha varchar)");
			st.execute(
					"create table if not exists local (id_local integer primary key autoincrement, codigo int, nome varchar, endereco varchar, numero varchar, ponto_referencia varchar, cidade varchar, estado varchar)");

			st.close();

		}

		catch (Exception e) {

			MessageBox.showException(e, true);

			exit(0);

		}

		Toast.posY = CENTER;

	}

	public void onEvent(Event e) {

		try {

			switch (e.type) {

			case ControlEvent.PRESSED:

				if (e.target == btClear) {

					clear();

					/*
					 * } else if (e.target == btInsert) {
					 * 
					 * doInsert();
					 */
				} else if (e.target == btDial && edPhone.getTrimmedLength() > 0) {

					Dial.number(edPhone.getText());

				} else if (e.target == btTeste) {
					System.out.println("EVENTO!!");
					login();
				}
				break;

			}

		}

		catch (Exception ee) {

			MessageBox.showException(ee, true);

		}

	}

	private void doInsert() throws SQLException, InvalidDateException {

		if (edName.getLength() == 0 || edBorn.getLength() == 0 || edPhone.getLength() == 0)

			Toast.show("Please fill all fields!", 2000);

		else {

			String name = edName.getText();

			Date born = new Date(edBorn.getText());

			String phone = edPhone.getText();

			Statement st = dbcon.createStatement();

			st.executeUpdate("insert into person values('" + name + "','" + born.getSQLString() + "','" + phone + "')");

			st.close();

			clear();

			Toast.show("Data inserted successfully!", 2000);

		}

	}

	private void login() {
		if (usuario.getLength() == 0 || senha.getLength() == 0) {

			Toast.show("Por favor, preencha todos os campos!", 2000);

		} else {
			if (usuario.getText().equals("anderson") && senha.getText().equals("admin")) {
				TelaInicial ti = new TelaInicial();
				ti.popup();
				Vm.sleep(2000); // or do something else
				ti.unpop();
			} else {
				Toast.show("Usuario e senha não conferem! Tente novamente.", 2000);
			}
		}
	}
}
