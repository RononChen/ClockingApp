package uac.imsp.clockingapp.View.activity;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.LoginController;
import uac.imsp.clockingapp.Controller.util.ILoginController;
import uac.imsp.clockingapp.Models.UpdateReceiver;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ILoginView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Login extends AppCompatActivity
                   implements View.OnClickListener , TextWatcher,
        ILoginView {
    private ImageView eye;
    private int Number;

    private AlertDialog.Builder builder;
    private  EditText Username,Password;

    private AlarmManager manager;
    private PendingIntent pendingIntent;
    private static final int REQ_CODE = 0;



    ILoginController loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loginPresenter = new LoginController(this);
        Intent alarmIntent = new Intent(this, UpdateReceiver.class);
         pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), REQ_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000, pendingIntent);
        //manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 84600 * 1000, pendingIntent);



    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login_button)
            loginPresenter.onLogin(Username.getText().toString(),
                Password.getText().toString());
        else if(v.getId()==R.id.login_show_password)
            loginPresenter.onShowHidePassword();
    }

    @Override
    public void onLoginSuccess(int number) {
        Number=number;



    }

    @Override

    public void onLoginError(String message) {

        new ToastMessage(this,message);
    }

    @Override
    public void onClocking() {
    Intent intent=new Intent(Login.this,ClockInOut.class);
    startActivity(intent);

    }

    @Override
    public void onPasswordError(String message) {
        Password.setError(message);

    }

    @Override
    public void onUsernameError(String message) {
        Username.setError(message);

    }

    @Override
    public void onShowHidePassword() {
            if(Password.getTransformationMethod().
                    equals(PasswordTransformationMethod.getInstance()))
            {
                eye.setImageResource(R.drawable.ic_baseline_visibility_off_18);


                //show password
                Password.setTransformationMethod(HideReturnsTransformationMethod.
                        getInstance());
            }
            else{
                eye.setImageResource(R.drawable.baseline_visibility_black_18);


                //hide password
                Password.setTransformationMethod(PasswordTransformationMethod.
                        getInstance());
            }


        }

    @Override
    public void onMaxAttempsReached(String message) {
        Intent intent;
        intent = new Intent(Login.this,StartScreen.class);

         new ToastMessage(this,message);


        finish();
        startActivity(intent);
    }

    @Override
    public void askWish(String pos, String neg, String title, String message) {
        builder=new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(pos, (dialog, which)
                        -> loginPresenter.onConfirmResult(true))

                .setNegativeButton(neg, (dialog, which)
                        -> loginPresenter.onConfirmResult(false));

        AlertDialog alert = builder.create();
        alert.setTitle(title);

        alert.show();

    }

    @Override
    public void onPositiveResponse(String message) {
       /* Intent intent=new Intent(Login.this, GeneralMenu.class);
        new ToastMessage(this,message);
        startActivity(intent);*/

        Intent intent = new Intent(this, GeneralMenu.class);
        intent.putExtra("CURRENT_USER",Number);
        new ToastMessage(this,message);

        startActivity(intent);
        Username.setText("");
        Password.setText("");
          }

    @Override
    public void onNegativeResponse(String message) {
        Intent intent=new Intent(this,SimpleEmployeeMenu.class);
        //intent.putExtra("CURRENT_")
        new ToastMessage(this,message);
        startActivity(intent);


    }


    public void initView(){

        Username=findViewById(R.id.login_username);
        Password=findViewById((R.id.login_password));
        eye=findViewById(R.id.login_show_password);
        eye.setOnClickListener(this);

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(this);
        Username.addTextChangedListener(this);
        Password.addTextChangedListener(this);

        Password.setOnClickListener(this);



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        if(Username.getText().toString().equals(s.toString()))
            loginPresenter.onUsernameEdit(s.toString());
        else if(Password.getText().toString().equals(s.toString()))

            loginPresenter.onPasswordEdit(s.toString());


    }
}
