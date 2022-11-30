package uac.imsp.clockingapp.Controller.control.settings.others;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.settings.others.IDarkModeController;
import uac.imsp.clockingapp.View.util.settings.others.IDarkModeView;

public class DarkModeController implements IDarkModeController {
	IDarkModeView darkModeView;
	private Context context;
	public DarkModeController(IDarkModeView darkModeView){
		this.darkModeView=darkModeView;
		this.context=(Context) this.darkModeView;


	}

	@Override
	public void onDarkMode() {
darkModeView.onDarkMode();
	}
}
