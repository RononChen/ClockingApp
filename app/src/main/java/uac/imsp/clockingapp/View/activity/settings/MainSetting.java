
package uac.imsp.clockingapp.View.activity.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import uac.imsp.clockingapp.BuildConfig;
import uac.imsp.clockingapp.Controller.control.settings.MainSettingsController;
import uac.imsp.clockingapp.Controller.util.settings.IMainSettingsController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.settings.IMainSettingsView;

public class MainSetting extends AppCompatActivity implements View.OnClickListener, IMainSettingsView {
TextView share,shareViaQR,overview,appVersion,clearCache,cacheSize,name,email,desc;
	EditText input;
Intent intent;
LinearLayout nameLayout,emailLayout,descLayout;
long cacheSizeValue;
String dialogInput;
boolean submited=false;
SharedPreferences preferences;
	final String PREFS_NAME="MyPrefsFile";
	SharedPreferences.Editor editor;

IMainSettingsController mainSettingsPresenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_setting);
		mainSettingsPresenter=new MainSettingsController(this);
		initView();
		retrieveSharedPreferences();
	}
	public void initView(){

		share=findViewById(R.id.setting_share);
		shareViaQR=findViewById(R.id.setting_share_with_qr);
		overview=findViewById(R.id.setting_overview);
		appVersion=findViewById(R.id.setting_version);
		clearCache=findViewById(R.id.setting_clear_cache);
		cacheSize=findViewById(R.id.setting_cache_size);
		nameLayout=findViewById(R.id.setting_name_layout);
		nameLayout.setOnClickListener(this);
		name=findViewById(R.id.setting_name);
		emailLayout=findViewById(R.id.setting_mail_layout);
		emailLayout.setOnClickListener(this);
		descLayout=findViewById(R.id.setting_description_layout);
		descLayout.setOnClickListener(this);

		cacheSize();
		cacheSize.setText(String.valueOf(cacheSizeValue));
		appVersion.setText(BuildConfig.VERSION_NAME);
		share.setOnClickListener(this);
		shareViaQR.setOnClickListener(this);
		overview.setOnClickListener(this);



	}



	@Override
	public void onClick(View v) {
if(v.getId()==R.id.setting_share)
	mainSettingsPresenter.onShareApp();
else if(v.getId()==R.id.setting_share_with_qr)
	mainSettingsPresenter.onShareAppViaQRCode();
else if(v.getId()==R.id.setting_overview)
	mainSettingsPresenter.onOverview();
else if (v.getId()==R.id.setting_clear_cache)
	mainSettingsPresenter.onClearAppCache();
else if (v.getId()==R.id.setting_name_layout)
	mainSettingsPresenter.onName();
else if(v.getId()==R.id.setting_mail_layout)
	mainSettingsPresenter.onEmail();
else if(v.getId()==R.id.setting_description_layout)
	mainSettingsPresenter.onDescription();
	}

	@Override
	public void onShareApp(String msg) {
		intent=new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT,msg);
		intent.setType("text/plain");
		startActivity(Intent.createChooser(intent, "Partager avec avec"));

	}

	public void retrieveSharedPreferences(){
		preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
	name.setText(preferences.getString("entrepriseName",""));
	email.setText(preferences.getString("entrepriseEmail",""));
	desc.setText(preferences.getString("entrepriseDescription",""));

	}

	@Override
	public void onShareAppViaQRCode() {
		intent=new Intent(this,ShareAppViaQRCode.class);
		startActivity(intent);

	}

	@Override
	public void onAppcacheCleared() {
cacheSize.setText(String.valueOf(cacheSizeValue));
	}

	@Override
	public void onName(String title, String msg, String pos, String neg) {
		inputDialog(title,msg,pos,neg);
		if(submited)
		{
			editor=preferences.edit();
			editor.putString("entrepriseName",dialogInput);
		}

		submited=false;
	}

	@Override
	public void onEmail(String title, String msg, String pos, String neg) {
		inputDialog(title,msg,pos,neg);
		if(submited)
		{
			editor=preferences.edit();
			editor.putString("entrepriseEmail",dialogInput);
		}

		submited=false;

	}

	@Override
	public void onDescription(String title, String msg, String pos, String neg) {
		inputDialog(title,msg,pos,neg);
		if(submited)
		{
			editor=preferences.edit();
			editor.putString("entrepriseDescription",dialogInput);
		}

		submited=false;

	}


	@Override
	public void onOverview() {

	}
	public void cacheSize(){
		cacheSizeValue=0;
		File[] files = getCacheDir().listFiles();
		assert files != null;
		for (File f:files) {
			cacheSizeValue = cacheSizeValue+f.length();
		}

	}
	public void  inputDialog(String title,String msg, String pos, String neg ){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		input = new EditText(MainSetting.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		builder.setView(input);
		builder.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton(pos, (dialog, which) -> {

					dialogInput=input.getText().toString();
					submited=true;
				})
				.setNegativeButton(neg,(dialog,which)->{

				});

		AlertDialog alert = builder.create();
		alert.setTitle(title);
		alert.show();

	}
}