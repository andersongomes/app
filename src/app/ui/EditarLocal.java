package app.ui;

import app.dao.LocalDAO;
import app.model.Local;
import totalcross.io.IOException;
import totalcross.map.GoogleMaps;
import totalcross.sys.InvalidNumberException;
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

public class EditarLocal extends Window {

	private Edit nome, endereco, numero, pontoReferencia, cidade, estado;
	private Button salvar, cancelar, btClear;
	String id;
	@SuppressWarnings(value = "unused")
	private GoogleMaps gm;
	private String latitude, longitude;
	private LocalDAO localDAO;
	private Local local;

	public EditarLocal(String id) {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);
		this.id = id;
		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Edite o Lugar"), CENTER, TOP + 50);

		add(new Label("Nome: "), LEFT + 100, AFTER);
		add(nome = new Edit(), LEFT, SAME);
		nome.setRect(LEFT + 100, AFTER, FILL-100, 25);

		add(new Label("Endereço: "), LEFT + 100, AFTER);
		add(endereco = new Edit(), LEFT, SAME);
		endereco.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Número: "), LEFT + 100, AFTER);
		add(numero = new Edit(), LEFT, SAME);
		numero.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Ponto de Referência: "), LEFT + 100, AFTER);
		add(pontoReferencia = new Edit(), LEFT, SAME);
		pontoReferencia.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Cidade: "), LEFT + 100, AFTER);
		add(cidade = new Edit(), LEFT, SAME);
		cidade.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Estado: "), LEFT + 100, AFTER);
		add(estado = new Edit(), LEFT, SAME);
		estado.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		localDAO = new LocalDAO();
		local = localDAO.selecionarPorId(id);

		nome.setText(local.getNome());
		endereco.setText(local.getEndereco());
		numero.setText(local.getNumero());
		pontoReferencia.setText(local.getPontoReferencia());
		cidade.setText(local.getCidade());
		estado.setText(local.getEstado());

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
					if (nome.getLength() == 0 || endereco.getLength() == 0 || numero.getLength() == 0
							|| pontoReferencia.getLength() == 0 || cidade.getLength() == 0 || estado.getLength() == 0) {
						Toast.show("Por favor, preencha todos os campos!!", 2000);
					} else {
						gm = new GoogleMaps();
						double[] x = null;
						try {
							x = GoogleMaps.getLocation(endereco.getText() + " " + numero.getText() + ", "
									+ cidade.getText() + ", " + estado.getText());
						} catch (IOException | InvalidNumberException eee) {
							latitude = "0";
							longitude = "0";
						}
						if (x == null) {
							latitude = "0";
							longitude = "0";
						} else {
							latitude = Double.toString(x[0]);
							longitude = Double.toString(x[1]);
						}
						local = new Local(Integer.parseInt(id), nome.getText(), endereco.getText(), numero.getText(),
								pontoReferencia.getText(), cidade.getText(), estado.getText(), latitude, longitude);

						localDAO = new LocalDAO();
						localDAO.editar(local);

						Toast.show("Local editado com sucesso! " + "Nova Latitude = " + latitude
								+ " / Nova Longitude = " + longitude, 5000);

						ListaLocais ll = new ListaLocais();
						ll.popup();
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
