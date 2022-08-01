package uac.imsp.clockingapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.ILoginController;
import uac.imsp.clockingapp.Controller.LoginController;
import uac.imsp.clockingapp.View.ILoginView;

public class Login extends AppCompatActivity
                   implements View.OnClickListener ,
        ILoginView {
    private EditText Username,Password;
    private  SharedPreferences preferences;
    int currentVersionCode,savedVersionCode;
    final String PREFS_NAME="MyPrefsFile",
            PREF_VERSION_CODE_KEY="version_code";
    final int DOESNT_EXIST=-1;


    ILoginController loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginController(this);
        // Get current version code
        preferences=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        savedVersionCode=preferences.getInt(PREF_VERSION_CODE_KEY,DOESNT_EXIST);
        currentVersionCode= BuildConfig.VERSION_CODE;
        loginPresenter.onLoad(savedVersionCode,currentVersionCode);
        //update the shares preferences with the current version code

        preferences.edit().putInt(PREF_VERSION_CODE_KEY,currentVersionCode).apply();


    }


    @Override
    public void onClick(View v) {
        loginPresenter.onLogin(Username.getText().toString(),Password.getText().toString());
    }

    @Override
    public void onLoginSuccess(String message) {
        Username.setText("");
        Password.setText("");
        Intent intent = new Intent(this,Menu.class);
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

        startActivity(intent);

    }

    @Override
    public void onLoginError(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFirstRun() {
        Intent intent=new Intent(Login.this,SetUp.class);
        //stop Login activity
        Login.this.finish();
        // start SetUp activity
        startActivity(intent);

    }

    @Override
    public void onNormalRun() {

        setContentView(R.layout.activity_login);
        initView();

    }

    @Override
    public void onUpgrade() {

    }



    public void initView(){
        Username=findViewById(R.id.login_username);
        Password=findViewById((R.id.login_password));
        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(this);

    }
}
