package uac.imsp.clockingapp.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.StartScreenController;
import uac.imsp.clockingapp.Controller.util.IStartScreenController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IStartScreenView;

public class StartScreen extends AppCompatActivity
implements View.OnClickListener  , IStartScreenView {

    private Intent intent;
    private IStartScreenController startScreenPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        initView();
        startScreenPresenter=new StartScreenController(this);


    }
    public void initView(){
        Button login = findViewById(R.id.start_screen_login_button);
        Button handler=findViewById(R.id.start_screen_file_handler);
        handler.setOnClickListener(this);
        Button clocking = findViewById(R.id.start_screen_clock_button);
        login.setOnClickListener(this);
        clocking.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent =new Intent(this,FileHandler.class) ;
        if(v.getId()==R.id.start_screen_login_button)
            startScreenPresenter.onLogin();
        else if(v.getId()==R.id.start_screen_clock_button)
            startScreenPresenter.onClocking();
        else if(v.getId()==R.id.start_screen_file_handler)
            startActivity(intent);


    }

    @Override
    public void onLogin() {
        intent=new Intent(StartScreen.this,Login.class);
        startActivity(intent);


    }

    @Override
    public void onClocking() {
        intent=new Intent(StartScreen.this,ClockInOut.class);
        startActivity(intent);

    }
}