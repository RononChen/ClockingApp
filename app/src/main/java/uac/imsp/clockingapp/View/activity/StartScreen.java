package uac.imsp.clockingapp.View.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

import uac.imsp.clockingapp.BuildConfig;
import uac.imsp.clockingapp.Controller.control.StartScreenController;
import uac.imsp.clockingapp.Controller.util.IStartScreenController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IStartScreenView;

public class StartScreen extends AppCompatActivity
implements View.OnClickListener  , IStartScreenView {

    private Intent intent;
    private IStartScreenController startScreenPresenter;
    private TextView date;
    private Handler timeHandler;
    private Runnable updater;
    int currentVersionCode,savedVersionCode;
    final String PREFS_NAME="MyPrefsFile",
            PREF_VERSION_CODE_KEY="version_code";
    final int DOESNT_EXIST=-1;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        startScreenPresenter = new StartScreenController(this);

        //initializing
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        savedVersionCode= preferences.getInt(PREF_VERSION_CODE_KEY,DOESNT_EXIST);
        // Get current version code
        currentVersionCode= BuildConfig.VERSION_CODE;
        startScreenPresenter.onLoad(savedVersionCode,currentVersionCode);


        //update the shared preferences with the current version code
        editor.putInt(PREF_VERSION_CODE_KEY,currentVersionCode);
        editor.apply();
        setContentView(R.layout.activity_start_screen);
        startScreenPresenter=new StartScreenController(this);
        initView();


    }

    public void initView(){
        AtomicReference<LocalDateTime> now=null;

        String currentDate;
        DateTimeFormatter dateTimeFormatter;
        dateTimeFormatter =DateTimeFormatter.ofPattern("dd/MM/yyyy/ HH:mm:ss");
        date=findViewById(R.id.start_screen_date);



        Button login = findViewById(R.id.start_screen_login_button);
        // -Button handler=findViewById(R.id.start_screen_file_handler);
        //-handler.setOnClickListener(this);
        Button clocking = findViewById(R.id.start_screen_clock_button);
        login.setOnClickListener(this);
        clocking.setOnClickListener(this);

        //date.setText(currentDate);

          /*timeHandler=new Handler();

          updater= () -> {
              now.set(LocalDateTime.now());
              currentDate = dateTimeFormatter.format(now.get());
              date.setText(currentDate);
              timeHandler.postDelayed(updater,1000);

          };

          timeHandler.postDelayed(updater,1000);*/


        //startClock();






    }

    @Override
    public void onClick(View v) {
        //-Intent intent =new Intent(this,FileHandler.class) ;
        if(v.getId()==R.id.start_screen_login_button)
            startScreenPresenter.onLogin();
        else if(v.getId()==R.id.start_screen_clock_button)
            startScreenPresenter.onClocking();
        /* - else if(v.getId()==R.id.start_screen_file_handler)
            startActivity(intent);*/


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

    @Override
    public void onFirstRun() {
        Intent intent=new Intent(StartScreen.this, SetUp.class);
        //stop Login activity
        StartScreen.this.finish();
        //For entreprise informations settings
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();

        editor.putString("entrepriseName","");
        editor.putString("entrepriseEmail","");
        editor.putString("entrepriseDescription","");
        //For userDoc
        editor.putString("userDoc","");
       // editor.apply();


        editor.putBoolean("emailAsUsername",false);
        editor.putBoolean("generateUsername",false);
        editor.putBoolean("editUsername",true);
        editor.putBoolean("generatePassword",false);
        editor.putBoolean("showPasswordDuringAdd",false);
        //For others settings
        editor.putBoolean("darkMode",false);
        editor.putString("language","Français");
        editor.putBoolean("notifyAdd",true);
        editor.putBoolean("notifyDelete",false);
        editor.putBoolean("notifyUpdate",false);
        editor.putBoolean("useFingerprint",false);
        editor.putBoolean("useQRCode",true);
        editor.putString("lang","fr");


        // start SetUp activity
        startActivity(intent);

    }

    @Override
    public void onUpgrade() {

    }

    @Override
    public void onNormalRun() {
        setContentView(R.layout.activity_start_screen);
        initView();
    }
  /*  public void startClock( ){
         timeHandler.post(updater);
    }*/
}