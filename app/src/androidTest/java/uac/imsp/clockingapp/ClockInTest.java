package uac.imsp.clockingapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.runner.RunWith;

import uac.imsp.clockingapp.Controller.control.ClockingInOutController;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.View.util.IClockInOutView;

@RunWith(AndroidJUnit4.class)
public class ClockInTest implements IClockInOutView {
	private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
	private ClockingInOutController clockingInOutPresenter;
	public ClockInTest() {
		String time;
		Day day=new Day(12,1);
		clockingInOutPresenter=new ClockingInOutController(this,appContext);
		time="07-00";
		clockingInOutPresenter.clock(1,day.getDate(),time);
	}

	@Override
	public void onLoad() {

	}

	@Override
	public void onClockInSuccessful() {

	}

	@Override
	public void onClockOutSuccessful() {

	}

	@Override
	public void onClockingError(int errorNumber) {

	}
}
