package app.ui;

import java.sql.SQLException;

import app.model.Local;
import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.ResultSet;
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

public class Editar extends Window{
	
	private Edit nome, endereco, numero, pontoReferencia, cidade, estado;
	private Button salvar, cancelar, btClear;
	private Connection conn;
	String id;
	
	public Editar(String id) {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);
		this.id = id;
		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);
			
		add(new Label("Edite o Lugar"), CENTER, TOP + 50);

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

		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));
			Statement st = conn.createStatement();
			
			String query = "select * from local where codigo = " + id ;
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				nome.setText((String) rs.getObject("nome"));
				endereco.setText((String) rs.getObject("endereco"));
				numero.setText((String) rs.getObject("numero"));
				pontoReferencia.setText((String) rs.getObject("ponto_referencia"));
				cidade.setText((String) rs.getObject("cidade"));
				estado.setText((String) rs.getObject("estado"));
			}
				
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 300, PARENTSIZE + 10, PREFERRED);

		add(salvar = new Button("Editar"), LEFT, SAME, PARENTSIZE + 30, PREFERRED, sp);

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
						
						String query = "update local set nome = '" + local.getNome() + "', endereco = '" + local.getEndereco() +
								 "', numero = '" + local.getNumero() + "', ponto_referencia = '" + local.getPontoReferencia() + 
								 "', cidade = '" + local.getCidade() + "', estado = '" + local.getEstado() + "' where codigo = " + id;
						st.execute(query);

						st.close();
						conn.close();
						Toast.show("Local editado com sucesso!", 2000);
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
