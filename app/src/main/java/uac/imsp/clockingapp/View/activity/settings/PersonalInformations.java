package uac.imsp.clockingapp.View.activity.settings;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;

public class PersonalInformations extends AppCompatActivity {


	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			finish();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// calling the action bar
		ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_personal_informations);
	}


}