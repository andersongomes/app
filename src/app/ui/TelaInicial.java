package app.ui;

import java.sql.SQLException;

import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class TelaInicial extends Window {

	Button listar, addLocal, verificarGps;
	
	public TelaInicial() {
		super("App Seleção SoftSite", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);
		
		addLocal = new Button("Inserir Local");
		addLocal.setBackColor(Color.GREEN);
		addLocal.setForeColor(Color.WHITE);
		add(addLocal, LEFT, SAME);
		
		verificarGps = new Button("VERIFICAR LOCAL");
		verificarGps.setBackColor(Color.WHITE);
		add(verificarGps, CENTER, SAME);
		
		listar = new Button("LISTAR");
		listar.setBackColor(Color.GREEN);
		listar.setForeColor(Color.WHITE);
		add(listar, RIGHT, SAME);
		
		
	}

	public void onEvent(Event event) {
		if (event.type == ControlEvent.PRESSED && event.target == listar) {
			ListaLocais ll;
			try {
				ll = new ListaLocais();
				ll.popup();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (event.type == ControlEvent.PRESSED && event.target == addLocal){
			AddLocal al = new AddLocal();
        	al.popup();
		}
	}
}
