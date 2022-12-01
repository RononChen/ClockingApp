package uac.imsp.clockingapp.View.activity.settings.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.settings.others.LanguagesController;
import uac.imsp.clockingapp.Controller.util.settings.others.ILanguagesController;
import uac.imsp.clockingapp.LocalHelper;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.settings.others.ILanguagesView;

public class Languages extends AppCompatActivity implements ILanguagesView,
		RadioGroup.OnCheckedChangeListener {
	RadioGroup radioGroup;
	RadioButton en,fr;
	String currentLanguage;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	ILanguagesController languagesPresenter;
	final  String PREFS_NAME="MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_languages);
		initView();
		retrievePreferences();
		languagesPresenter=new LanguagesController(this);
		if(Objects.equals(currentLanguage, "fr"))
			fr.setChecked(true);
		else if(Objects.equals(currentLanguage, "en"))
			en.setChecked(true);

	}
	public void initView(){
		radioGroup=findViewById(R.id.languages);
		fr=findViewById(R.id.languages_fr);
		en=findViewById(R.id.languages_eng);
		radioGroup.setOnCheckedChangeListener(this);
	}
	public void retrievePreferences(){
		preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		editor=preferences.edit();
		currentLanguage=preferences.getString("lang","fr");
	}

	@Override
	public void onLanguageChanged(String lang) {
		LocalHelper.setLocale(Languages.this,lang);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		String lang = null;

		if(group.getId()==R.id.languages)
		{
			if(checkedId==R.id.languages_fr)
				lang="fr";

			else if(checkedId==R.id.languages_eng)
			lang="en";
			editor.putString("lang",lang);
			editor.apply();
			onLanguageChanged(lang);
		}


	}
}