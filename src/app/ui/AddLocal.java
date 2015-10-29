package app.ui;

import app.model.Local;
import app.utils.ConnectionFactory;
import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.Statement;
import totalcross.sys.Convert;
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

public class AddLocal extends Window {

	private Edit nome, endereco, numero, pontoReferencia, cidade, estado;
	private Button salvar, cancelar, btClear;
	private Connection conn;
	public AddLocal() {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Cadastre um Lugar"), CENTER, TOP + 50);

		add(new Label("Nome: "), LEFT, AFTER + 100);
		add(nome = new Edit(), LEFT, AFTER);

		add(new Label("Endereço: "), LEFT, AFTER + 50);
		add(endereco = new Edit(), LEFT, AFTER);

		add(new Label("Número: "), LEFT, AFTER + 50);
		add(numero = new Edit(), LEFT, AFTER);

		add(new Label("Ponto de Referência: "), LEFT, AFTER + 50);
		add(pontoReferencia = new Edit(), LEFT, AFTER);

		add(new Label("Cidade: "), LEFT, AFTER + 50);
		add(cidade = new Edit(), LEFT, AFTER);

		add(new Label("Estado: "), LEFT, AFTER + 50);
		add(estado = new Edit(), LEFT, AFTER);

		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 300, PARENTSIZE + 10, PREFERRED);

		add(salvar = new Button("Salvar"), LEFT, SAME, PARENTSIZE + 30, PREFERRED, sp);

		add(btClear = new Button("Limpar"), CENTER, SAME, PARENTSIZE + 30, PREFERRED, sp);

		add(cancelar = new Button("Cancelar"), RIGHT, SAME, PARENTSIZE + 30, PREFERRED, sp);

	}

	public void onEvent(Event e) {

		try {

			switch (e.type) {

			case ControlEvent.PRESSED:

				if (e.target == btClear) {
					clear();
				} else if (e.target == salvar) {
					if (nome.getLength() == 0 || endereco.getLength() == 0 || numero.getLength() == 0
							|| pontoReferencia.getLength() == 0 || cidade.getLength() == 0 || estado.getLength() == 0) {
						Toast.show("Por favor, preencha todos os campos!!", 2000);
					} else {

						Local local = new Local();
						local.setCidade(cidade.getText());
						local.setEndereco(endereco.getText());
						local.setEstado(estado.getText());
						local.setNome(nome.getText());
						local.setNumero(numero.getText());
						local.setPontoReferencia(pontoReferencia.getText());
						
						conn = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));

						Statement st = conn.createStatement();
						
						String query = "insert into local (nome, endereco, numero, ponto_referencia, cidade, estado) "
								+ "values ('" + local.getNome() + "', '" + local.getEndereco() + "', '"
								+ local.getNumero() + "', '" + local.getPontoReferencia() + "',  '" + local.getCidade()
								+ "', '" + local.getEstado() + "')";
						st.execute(query);

						st.close();
						conn.close();
						Toast.show("Local cadastrado com sucesso!", 2000);
						ListaLocais ll = new ListaLocais();
						ll.popup();
					}
				} else if (e.target == cancelar) {
					TelaInicial ti = new TelaInicial();
					ti.popup();
				}

				break;

			}

		}

		catch (Exception ee) {

			MessageBox.showException(ee, true);

		}

	}

}
