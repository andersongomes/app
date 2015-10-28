package app.sample;

import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class TelaInicial extends Window {

	Button btnHi;

	public TelaInicial() {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;
		
		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(btnHi = new Button("Hi!"), CENTER, CENTER);
	}

	public void onEvent(Event event) {
		if (event.type == ControlEvent.PRESSED && event.target == btnHi) {
			unpop(); // a WINDOW_CLOSED event will be posted to this PARENT
						// window.
		}
	}
}
