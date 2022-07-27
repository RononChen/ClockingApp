package uac.imsp.clockingapp;


import android.content.Intent;
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
    ILoginController loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        loginPresenter = new LoginController(this);

    }


    @Override
    public void onClick(View v) {
        loginPresenter.onLogin(Username.getText().toString(),Password.getText().toString());
    }

    @Override
    public void onLoginSuccess(String message) {
        Intent intent = new Intent(this,Menu.class);
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    @Override
    public void onLoginError(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public void initView(){
        Username=findViewById(R.id.login_username);
        Password=findViewById((R.id.login_password));
        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(this);

    }
}
