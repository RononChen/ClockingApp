package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.ISimpleEmployeeMenuController;
import uac.imsp.clockingapp.View.util.ISimpleEmployeeMenuView;

public class SimpleEmployeeMenuController  implements ISimpleEmployeeMenuController {
	ISimpleEmployeeMenuView simpleEmployeeMenuView;
	private final Context context;


	public SimpleEmployeeMenuController(ISimpleEmployeeMenuView simpleEmployeeMenuView){
this.simpleEmployeeMenuView=simpleEmployeeMenuView;
context= (Context) this.simpleEmployeeMenuView;

	}

	@Override
	public void onClocking() {

	}

	@Override
	public void onConsultatisticsMenu() {

	}

	@Override
	public void onConsultPresentReport() {

	}

	@Override
	public void onExit() {

	}

	@Override
	public void onSettings() {

	}
}
