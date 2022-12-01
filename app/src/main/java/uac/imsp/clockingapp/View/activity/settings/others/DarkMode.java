package uac.imsp.clockingapp.View.activity.settings.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

import uac.imsp.clockingapp.Controller.control.settings.others.DarkModeController;
import uac.imsp.clockingapp.Controller.util.settings.others.IDarkModeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.settings.others.IDarkModeView;

public class DarkMode extends AppCompatActivity
		implements CompoundButton.OnCheckedChangeListener, IDarkModeView {
	SwitchMaterial darkMode;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	Boolean DarkMode;
	IDarkModeController darkModePresenter;
	String 	PREFS_NAME="MyPrefsFile";
	boolean isChecked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		darkModePresenter=new DarkModeController(this);
		setContentView(R.layout.activity_dark_mode);
		initView();
		retrieveSharedPreferences();
		darkMode.setChecked(DarkMode);

	}
	public void initView(){
darkMode=findViewById(R.id.dark_mode);
darkMode.setOnCheckedChangeListener(this);
	}
	public void retrieveSharedPreferences(){
		preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		editor=preferences.edit();

DarkMode=preferences.getBoolean("darkMode",false);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
if(buttonView.getId()==R.id.dark_mode)
{
	this.isChecked=isChecked;
	darkModePresenter.onDarkMode();
}


	}

	@Override
	public void onDarkMode() {
		editor.putBoolean("darkMode",isChecked);
		editor.apply();

	}
}