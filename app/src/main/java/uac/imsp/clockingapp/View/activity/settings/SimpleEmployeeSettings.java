package uac.imsp.clockingapp.View.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.activity.settings.others.DarkMode;
import uac.imsp.clockingapp.View.activity.settings.others.Help;
import uac.imsp.clockingapp.View.activity.settings.others.Languages;
import uac.imsp.clockingapp.View.activity.settings.others.ReportProblem;

public class SimpleEmployeeSettings extends AppCompatActivity implements View.OnClickListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_employee_settings);

		setContentView(R.layout.activity_general_menu);
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		initView();
	}
	public void initView(){
		 final TextView dark=findViewById(R.id.setting_dark),
				 lang=findViewById(R.id.setting_languages),
				 problem=findViewById(R.id.setting_problem),
				 help=findViewById(R.id.setting_help),
				 personalInfos=findViewById(R.id.setting_personal_infos),
				accountSettings=findViewById(R.id.setting_my_account);
		 dark.setOnClickListener(this);
		lang.setOnClickListener(this);
		problem.setOnClickListener(this);
		help.setOnClickListener(this);
		personalInfos.setOnClickListener(this);
		accountSettings.setOnClickListener(this);

	}

	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.general_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(@NonNull View v) {
		if(v.getId()==R.id.setting_my_account){
			startActivity(new Intent(this,Account.class));

		}
		else if (v.getId()==R.id.setting_personal_infos){

		}
		else if (v.getId()==R.id.setting_docs){

		}

		else if (v.getId()==R.id.setting_dark){
			startActivity(new Intent(this, DarkMode.class));

		}

		else if (v.getId()==R.id.setting_languages){

			startActivity(new Intent(this, Languages.class));
		}

		else if (v.getId()==R.id.setting_problem){
			startActivity(new Intent(this, ReportProblem.class));

		}

		else if (v.getId()==R.id.setting_help){
			startActivity(new Intent(this, Help.class));

		}

	}
}