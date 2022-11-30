package uac.imsp.clockingapp.View.activity.settings.others;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.settings.others.ClockingController;
import uac.imsp.clockingapp.Controller.util.settings.others.IClockingController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.settings.others.IClockingView;

public class Clocking extends AppCompatActivity
		implements RadioGroup.OnCheckedChangeListener, IClockingView {
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	RadioGroup radioGroup;
	RadioButton useQRCode,useFingerprint;
	boolean UseQRCode,UseFingerPrint;
	IClockingController clockingPresenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clocking);
		initView();
		retrieveSharedPreferences();
		clockingPresenter=new ClockingController(this);

	}
	public void initView(){
		radioGroup=findViewById(R.id.group);
		useQRCode =findViewById(R.id.use_qr_code);
		useFingerprint=findViewById(R.id.use_fingerprint);
		useQRCode.setSelected(UseQRCode);
		useFingerprint.setSelected(UseFingerPrint);
		radioGroup.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
if(group.getId()==R.id.group)
{
	if(checkedId==R.id.use_qr_code)
		clockingPresenter.onUseQRCode();

	else if(checkedId==R.id.use_fingerprint)
		clockingPresenter.onUseFingerPrint();
	editor.apply();
}

	}
	public void retrieveSharedPreferences(){
		UseQRCode =preferences.getBoolean("useQRCode",true);
		UseFingerPrint =preferences.getBoolean("useFingerprint",false);

	}

	@Override
	public void onUseQRCode() {
		editor.putBoolean("useQRCode",true);

	}

	@Override
	public void onUseFingerPrint() {
		editor.putBoolean("useFingerprint",true);

	}
}