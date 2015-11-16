package app.ui;

import app.dao.UsuarioDAO;
import app.model.Usuario;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class AddUsuario extends Window {

	private Edit nome, usuario, senha, resenha;
	private Button salvar, cancelar, btClear;

	@SuppressWarnings("static-access")
	public AddUsuario() {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Cadastre um Lugar"), CENTER, TOP + 50);

		add(new Label("Nome: "), LEFT + 100, AFTER);
		add(nome = new Edit(), LEFT, SAME);
		nome.setRect(LEFT + 100, AFTER, FILL-100, 25);

		add(new Label("Usuário: "), LEFT + 100, AFTER);
		add(usuario = new Edit(), LEFT, SAME);
		usuario.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Senha: "), LEFT + 100, AFTER);
		senha = new Edit();
		senha.setMode(senha.PASSWORD_ALL);
		add(senha, LEFT, SAME);
		senha.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Re-Senha: "), LEFT + 100, AFTER);
		resenha = new Edit();
		resenha.setMode(resenha.PASSWORD_ALL);
		add(resenha, LEFT, SAME);
		resenha.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);

		add(salvar = new Button("Salvar"), LEFT + 100, SAME, PREFERRED+100, 25, sp);
		salvar.setBackColor(Color.GREEN);
		salvar.setForeColor(Color.BLACK);

		add(btClear = new Button("Limpar"), CENTER, SAME, PREFERRED+100, 25, sp);

		add(cancelar = new Button("Cancelar"), RIGHT - 100, SAME, PREFERRED+100, 25, sp);
		cancelar.setBackColor(Color.RED);
		cancelar.setForeColor(Color.WHITE);
	}

	public void onEvent(Event e) {
		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == btClear) {
					clear();
				} else if (e.target == salvar) {
					if (nome.getLength() == 0 || usuario.getLength() == 0 || senha.getLength() == 0
							|| resenha.getLength() == 0) {
						Toast.show("Por favor, preencha todos os campos!!", 2000);
					} else{
						if(!senha.getText().equals(resenha.getText())){
							Toast.show("Senha diferente da confirmação da senha!!", 2000);
						} else {
							Usuario user = new Usuario(nome.getText(), usuario.getText(), senha.getText());
							UsuarioDAO usuarioDAO = new UsuarioDAO();
							usuarioDAO.salvar(user);
	
							Toast.show("Usuario cadastrado com sucesso! ", 5000);
	
							ListaUsuarios lu = new ListaUsuarios();
							lu.popup();
						}	
					}
				} else if (e.target == cancelar) {
					TelaInicial ti = new TelaInicial();
					ti.popup();
				}
				break;
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}
}
