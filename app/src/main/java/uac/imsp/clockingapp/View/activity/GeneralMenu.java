package uac.imsp.clockingapp.View.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.GeneralMenuController;
import uac.imsp.clockingapp.Controller.util.IGeneralMenuController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IGeneralMenuView;

public class GeneralMenu extends AppCompatActivity implements View.OnClickListener,
        IGeneralMenuView {

    IGeneralMenuController menuPresenter;
    private int currentUser;
    Intent intent ;



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
            case R.id.dash:
                startActivity(new Intent(this,Menu.class));
                break;
           /* case R.id.add :
                menuPresenter.onRegisterEmployeeMenu(currentUser);
                break;
            case R.id.update:
                menuPresenter.onUpdateEmployeeMenu(currentUser);
                break;
            case R.id.delete:
                menuPresenter.onDeleteEmployeeMenu(currentUser);
                break;
            case R.id.clock:
                menuPresenter.onClocking();
                break;
            case R.id.presence_report:
                menuPresenter.onConsultPresenceReport();
                break;
            case R.id.stat_by_service:
                menuPresenter.onConsultatisticsMenu(currentUser);
                break;
            case R.id.quit:

                menuPresenter.onExit();*/

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
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
       /* else if(v.getId()==R.id.menu_delete)
            menuPresenter.onDeleteEmployeeMenu(currentUser);*/
        else if (v.getId()==R.id.menu_search)
            menuPresenter.onSearchEmployeeMenu(currentUser);
        /*else if(v.getId()==R.id.menu_update)
            menuPresenter.onUpdateEmployeeMenu(currentUser);*/
        else if(v.getId()==R.id.menu_statistics_by_servie)
            menuPresenter.onConsultatisticsMenu(currentUser);
       /* else if (v.getId()==R.id.menu_presence_report)
            menuPresenter.onConsultPresenceReport();*/
        else if(v.getId()==R.id.menu_password)
            startActivity(new Intent(this,ChangePassword.class));
       /* else if(v.getId()==R.id.menu_statistics_by_employee)
             startActivity(new Intent(this,ConsultStatisticsByEmployee.class));*/


    }


    @Override
    public void onSearchEmployeeMenuError(String message) {

    Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateEmployeeMenuError(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    @Override

    public void onDeleteEmployeeMenuError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onRegisterEmployeeMenuError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onConsultatisticsMenuError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();


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
        Intent intent ;
        intent = new Intent(this, ConsultStatisticsByService.class);
        startActivity(intent);

    }

    @Override
    public void onClocking() {

        intent = new Intent(this, ClockInOut.class);
        startActivity(intent);

    }

    @Override
    public void onConsultPresenceReport() {
        intent=new Intent(this,ConsultPresenceReport.class);
        startActivity(intent);

    }

    @Override
    public void onExit(String pos, String neg, String title,String confirmationMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(confirmationMessage)
                .setCancelable(false)
                .setPositiveButton(pos, (dialog, which) -> System.exit(0))
                .setNegativeButton(neg, (dialog, which) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();

    }

    public void  initView(){
        Button register = findViewById(R.id.menu_register);
        Button clock = findViewById(R.id.menu_clock);
        Button search = findViewById(R.id.menu_search);
       // Button update = findViewById(R.id.menu_update);
       // Button delete = findViewById(R.id.menu_delete);
        Button statisticsByService = findViewById(R.id.menu_statistics_by_servie);
       /* Button presenceReport =findViewById(R.id.menu_presence_report);
        Button statisticsByEmployee=findViewById(R.id.menu_statistics_by_employee);*/


Button password=findViewById(R.id.menu_password);
        register.setOnClickListener(this);
        clock.setOnClickListener(this);
        search.setOnClickListener(this);
        search.setOnClickListener(this);
        //update.setOnClickListener(this);
       // delete.setOnClickListener(this);
        statisticsByService.setOnClickListener(this);
        //presenceReport.setOnClickListener(this);
        //statisticsByEmployee.setOnClickListener(this);
        password.setOnClickListener(this);
    }
}