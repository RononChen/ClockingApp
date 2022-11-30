package uac.imsp.clockingapp.Controller.control.settings.others;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.settings.others.IClockingController;
import uac.imsp.clockingapp.View.util.settings.others.IClockingView;

public class ClockingController implements IClockingController {
	IClockingView clockingView;
	private Context context;
	public ClockingController(IClockingView clockingView)
	{
		this.clockingView=clockingView;
		this.context= (Context) this.clockingView;

	}
	@Override
	public void onUseQRCode() {

		clockingView.onUseQRCode();
	}

	@Override
	public void onUseFingerPrint() {
		clockingView.onUseFingerPrint();

	}
}
