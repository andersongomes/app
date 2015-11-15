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

public class EditarUsuario extends Window {

	private Edit nome, usuario, senha, resenha;
	private Button salvar, cancelar, btClear;
	String id;
	private UsuarioDAO usuarioDAO;
	private Usuario user;

	@SuppressWarnings("static-access")
	public EditarUsuario(String id) {
		super("App Sele��o SoftSite", VERTICAL_GRADIENT);
		this.id = id;
		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Edite o Usuario"), CENTER, TOP + 50);

		add(new Label("Nome: "), LEFT + 100, AFTER);
		add(nome = new Edit(), LEFT, SAME);
		nome.setRect(LEFT + 100, AFTER, FILL-100, 25);

		add(new Label("Usuario: "), LEFT + 100, AFTER);
		add(usuario = new Edit(), LEFT, SAME);
		usuario.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Senha: "), LEFT + 100, AFTER);
		senha = new Edit();
		senha.setMode(senha.PASSWORD_ALL);
		add(senha, LEFT, SAME);
		senha.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Re-Senha: "), LEFT + 100, AFTER);
		resenha = new Edit();
		resenha.setMode(senha.PASSWORD_ALL);
		add(resenha, LEFT, SAME);
		resenha.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		usuarioDAO = new UsuarioDAO();
		user = usuarioDAO.selecionarPorId(id);

		nome.setText(user.getNome());
		usuario.setText(user.getUsuario());

		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);

		add(salvar = new Button("Editar"), LEFT + 100, SAME, PREFERRED+100, 25, sp);
		salvar.setBackColor(Color.GREEN);

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
					} else {
						if(!senha.getText().equals(resenha.getText())){
							Toast.show("Senha diferente da confirma��o da senha!!", 2000);
						} else {
							user = new Usuario(Integer.parseInt(id), nome.getText(), usuario.getText(), senha.getText());
	
							usuarioDAO = new UsuarioDAO();
							usuarioDAO.editar(user);
	
							Toast.show("Usuario editado com sucesso!!", 5000);
	
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
