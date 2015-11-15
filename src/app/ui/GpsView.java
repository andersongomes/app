package app.ui;

import totalcross.io.IOException;
import totalcross.io.device.PortConnector;
import totalcross.io.device.gps.GPS;
import totalcross.map.GoogleMaps;
import totalcross.sys.InvalidNumberException;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Label;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class GpsView extends Window {

	GPS gps;
	PortConnector portConnector;
	public double latitude;
	public double longitude;
	private Button cancelar;

	public GpsView() {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Tela de GPS"), CENTER, TOP + 50);
		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 300, PARENTSIZE + 10, PREFERRED);
		add(cancelar = new Button("Cancelar"), RIGHT, SAME, PARENTSIZE + 30, PREFERRED, sp);

		@SuppressWarnings("unused")
		GoogleMaps gm = new GoogleMaps();

		try {
			double[] x = GoogleMaps.getLocation("Rua Coronel Guilherme Alencar 881");
			Toast.show(Double.toString(x[0]), 2000);
		} catch (IOException | InvalidNumberException e) {
			MessageBox.showException(e, true);
		}

		/*
		 * try { double[] c = CellInfo.toCoordinates();
		 * Toast.show(Double.toString(c[0]), 2000); } catch (Exception e) {
		 * MessageBox.showException(e, true); } new Thread() { public void run()
		 * { try { gps = new GPS(); for (int i = 0; i < 60*2 && gps.location[0]
		 * == 0; i++) // wait 60s { Vm.safeSleep(500); try {
		 * gps.retrieveGPSData(); Toast.show(gps.toString(), 2000); } catch
		 * (Exception eee) { MessageBox.showException(eee, true);
		 * 
		 * break; } } } catch (IOException e) { MessageBox.showException(e,
		 * true);
		 * 
		 * }
		 * 
		 * } }.start();
		 */
	}

	public void onEvent(Event e) {
		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == cancelar) {
					TelaInicial ti = new TelaInicial();
					ti.popup();
				}
				break;
			}
		}catch (Exception ee) {
			MessageBox.showException(ee, true);
		}

	}

}
