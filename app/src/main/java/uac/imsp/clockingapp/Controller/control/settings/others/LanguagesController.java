package uac.imsp.clockingapp.Controller.control.settings.others;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.settings.others.ILanguagesController;
import uac.imsp.clockingapp.View.util.settings.others.ILanguagesView;

public class LanguagesController implements ILanguagesController {
	ILanguagesView languagesView;
	private Context context;
	public LanguagesController(ILanguagesView languagesView)
	{
		this.languagesView=languagesView;
		context=(Context) this.languagesView;


	}
	@Override
	public void onLanguageChanged() {

	}
}
