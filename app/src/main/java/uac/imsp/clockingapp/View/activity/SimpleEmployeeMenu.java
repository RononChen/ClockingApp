package uac.imsp.clockingapp.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.SimpleEmployeeMenuController;
import uac.imsp.clockingapp.Controller.util.ISimpleEmployeeMenuController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.activity.settings.SimpleEmployeeSettings;
import uac.imsp.clockingapp.View.util.ISimpleEmployeeMenuView;

public class SimpleEmployeeMenu extends AppCompatActivity
        implements ISimpleEmployeeMenuView , View.OnClickListener {
    ISimpleEmployeeMenuController simpleEmployeeMenuPresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_employee_menu);
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        simpleEmployeeMenuPresenter =
                new SimpleEmployeeMenuController(this);


    }
    public void initView(){
       final Button password=findViewById(R.id.menu_password);
        password.setOnClickListener(this);
        final Button clock=findViewById(R.id.menu_clock),
                report=findViewById(R.id.menu_presence_report),
                statistics=findViewById(R.id.menu_my_presence_statistics),
                settings=findViewById(R.id.menu_settings);
        clock.setOnClickListener(this);
        report.setOnClickListener(this);
        statistics.setOnClickListener(this);
        settings.setOnClickListener(this);

    }

    @Override
    public void onConsultatisticsMenuSuccessful() {
        startActivity(new Intent(this, ConsultStatisticsByEmployee.class));


    }

    @Override
    public void onClocking() {

        startActivity(new Intent(this, ClockInOut.class));


    }

    @Override
    public void onSettings() {
        startActivity(new Intent(this, SimpleEmployeeSettings.class));

    }

    @Override
    public void onConsultPresenceReport() {
        startActivity(new Intent(this,ConsultPresenceReport.class));


    }

    @Override
    public void onExit() {
  startActivity(new Intent(this,SimpleEmployeeMenu.class));

    }

    @Override
    public void onClick(@NonNull View v) {
if(v.getId() == R.id.menu_clock)
    simpleEmployeeMenuPresenter.onClocking();
else if(v.getId()==R.id.menu_presence_report)
    simpleEmployeeMenuPresenter.onConsultPresentReport();
else if(v.getId()==R.id.menu_my_presence_statistics)
    simpleEmployeeMenuPresenter.onConsultatisticsMenu();
else if(v.getId()==R.id.menu_settings)
    simpleEmployeeMenuPresenter.onSettings();

else if(v.getId()==R.id.menu_password)

    startActivity(new Intent(this,ChangePassword.class));

    }
}