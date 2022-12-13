package uac.imsp.clockingapp.View.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

	@Override
	public void onClick(@NonNull View v) {
		if(v.getId()==R.id.setting_my_account){

		}
		else if (v.getId()==R.id.setting_personal_infos){

		}
		else if (v.getId()==R.id.setting_docs){

		}

		else if (v.getId()==R.id.setting_dark){
			startActivity(new Intent(this, DarkMode.class));

		}

		else if (v.getId()==R.id.languages){

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