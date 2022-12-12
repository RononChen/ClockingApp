package uac.imsp.clockingapp.View.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.GeneralMenuController;
import uac.imsp.clockingapp.Controller.util.IGeneralMenuController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.activity.settings.MainSetting;
import uac.imsp.clockingapp.View.util.IGeneralMenuView;

public class GeneralMenu extends AppCompatActivity implements View.OnClickListener,
        IGeneralMenuView {

    IGeneralMenuController menuPresenter;
    private int currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_menu);
       initView();
       menuPresenter= new GeneralMenuController(this) ;
       currentUser=getIntent().getIntExtra("CURRENT_USER",0);
    }
        @Override


    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.general_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {

            case R.id.settings :

                startActivity(new Intent(this, MainSetting.class));

                break;
            case R.id.logout:

                menuPresenter.onExit();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(@NonNull View v) {
        boolean presenter;
        Intent intent;
         if(v.getId()==R.id.menu_register)
         {

            presenter= menuPresenter.onRegisterEmployeeMenu(currentUser);
           if(!presenter)//there is no service
           {
               intent=new Intent(GeneralMenu.this,NoServiceAvailable.class);
               intent.putExtra("CURRENT_USER",currentUser);

           }
         }
        else if(v.getId()==R.id.menu_clock)
            menuPresenter.onClocking();
        else if (v.getId()==R.id.menu_search)
            menuPresenter.onSearchEmployeeMenu(currentUser);

        else if(v.getId()==R.id.menu_statistics_by_servie)
            menuPresenter.onConsultatisticsMenu(currentUser);

        else if(v.getId()==R.id.menu_password)
            startActivity(new Intent(this,ChangePassword.class));



    }


    @Override
    public void onSearchEmployeeMenuSuccessfull() {
        Intent intent ;
        intent = new Intent(this, SearchEmployee.class);
        startActivity(intent);

    }

    @Override
    public void onUpdateEmployeeMenuSuccessfull() {
        Intent intent ;
        intent = new Intent(this, UpdateEmployee.class);
        startActivity(intent);

    }

    @Override
    public void onDeleteEmployeeMenuSucessfull() {
        Intent intent ;
        intent = new Intent(this, DeleteEmployee.class);
        startActivity(intent);

    }

    @Override
    public void onRegisterEmployeeMenuSuccessful() {
        Intent intent ;
        intent = new Intent(this, RegisterEmployee.class);

        startActivity(intent);

    }

    @Override
    public void onConsultatisticsMenuSuccessful() {
        startActivity(new Intent(this, ConsultStatisticsByService.class));

    }

    @Override
    public void onClocking() {


        startActivity(new Intent(this, ClockInOut.class));

    }

    @Override
    public void onConsultPresenceReport() {

        startActivity(new Intent(this,ConsultPresenceReport.class));

    }

    @Override
    public void onExit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.exit_confirmation_message))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel())
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    AlertDialog alert = builder.create();
                    alert.setTitle(getString(R.string.exit_confirmation));
                    alert.show();

                });
    }


    public void  initView(){
        Button register = findViewById(R.id.menu_register);
        Button clock = findViewById(R.id.menu_clock);
        Button search = findViewById(R.id.menu_search);

        Button statisticsByService = findViewById(R.id.menu_statistics_by_servie);



Button password=findViewById(R.id.menu_password);
        register.setOnClickListener(this);
        clock.setOnClickListener(this);
        search.setOnClickListener(this);
        search.setOnClickListener(this);

        statisticsByService.setOnClickListener(this);

        password.setOnClickListener(this);
    }
}