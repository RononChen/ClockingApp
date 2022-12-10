package uac.imsp.clockingapp.View.activity.settings;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.BuildConfig;
import uac.imsp.clockingapp.R;

public class Overview extends AppCompatActivity implements View.OnClickListener {
	Button cpyOverview;
	TextView appVersionName,sdkVersion,osVersion,deviceName,deviceManufacturer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		initView();
	}
	public void initView(){
		cpyOverview=findViewById(R.id.overview_copy);
		cpyOverview.setOnClickListener(this);

		appVersionName=findViewById(R.id.overview_app_version);
		appVersionName.setText(BuildConfig.VERSION_NAME);

		deviceName=findViewById(R.id.overview_device_name);
		deviceName.setText(Build.MODEL);
		deviceManufacturer=findViewById(R.id.overview_device_manufacturer);
		deviceManufacturer.setText(Build.MANUFACTURER);

		sdkVersion=findViewById(R.id.overview_sdk_version);
		sdkVersion.setText(Build.VERSION.SDK_INT);

		osVersion=findViewById(R.id.overview_version_name);
		osVersion.setText(Build.VERSION.RELEASE);


	}

	@Override
	public void onClick(@NonNull View v) {
		String overview;
		overview ="Version de l'application:\n" +
				"-"+appVersionName.getText().toString()+"\n"+
		"Nom de lappareil:\n" +
				"-"+appVersionName.getText().toString()+"\n"+
				"Nom du fabriquant:\n" +
				"-"+deviceManufacturer.getText().toString()+"\n"+
		"Version du système d'exploitation:\n"+
		"-"+osVersion.getText().toString()+"\n"+
		"Version du SDK:\n"+
		"-"+sdkVersion.getText().toString();

		ClipboardManager clipboard;
		ClipData clip;
if(v.getId()==R.id.overview_copy)
{
	clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	 clip = ClipData.newPlainText("overview", overview);
	clipboard.setPrimaryClip(clip);
}

	}
}