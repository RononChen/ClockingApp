package uac.imsp.clockingapp.View.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;

public class SetUp extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String PREFS_NAME="MyPrefsFile";
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        initView();
    }
    public void initView(){
        final Button next=findViewById(R.id.set_up_continue);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Services.class);
        finish();
startActivity(intent);
editor.putString("nextStep","service");
editor.apply();
    }
}
