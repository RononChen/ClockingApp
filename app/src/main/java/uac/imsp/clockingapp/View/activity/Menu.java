package uac.imsp.clockingapp.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.util.IMenuController;
import uac.imsp.clockingapp.Controller.control.MenuController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IMenuView;

public class Menu extends AppCompatActivity implements View.OnClickListener,
        IMenuView {

    IMenuController menuPresenter;
    private int currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       initView();
       menuPresenter= new MenuController(this) ;
       currentUser=getIntent().getIntExtra("CURRENT_USER",-1);
    }

    @Override

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    // @Override
    /*public boolean onCreateOptionsMenu(android.view.Menu menu) {
       // getMenuInflater().inflate(R.id.logout);
        return super.onCreateOptionsMenu(menu);
    }*/


    @Override
    public void onClick(View v) {
         if(v.getId()==R.id.menu_register)
            menuPresenter.onRegisterEmployeeMenu(currentUser);
        else if(v.getId()==R.id.menu_clock)
            menuPresenter.onClocking();

        else if(v.getId()==R.id.menu_delete)
            menuPresenter.onDeleteEmployeeMenu(currentUser);
        else if (v.getId()==R.id.menu_search)
            menuPresenter.onSearchEmployeeMenu(currentUser);
        else if(v.getId()==R.id.menu_update)
            menuPresenter.onUpdateEmployeeMenu(currentUser);
        else if(v.getId()==R.id.menu_statistics)
            menuPresenter.onConsultatisticsMenu(currentUser);

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
        intent = new Intent(this, ConsultStatistics.class);
        startActivity(intent);

    }

    @Override
    public void onClocking() {
        Intent intent ;
        intent = new Intent(this, ClockInOut.class);
        startActivity(intent);

    }

    public void  initView(){
        Button register = findViewById(R.id.menu_register);
        Button clock = findViewById(R.id.menu_clock);
        Button search = findViewById(R.id.menu_search);
        Button update = findViewById(R.id.menu_update);
        Button delete = findViewById(R.id.menu_delete);
        Button consultStatistics = findViewById(R.id.menu_statistics);


        register.setOnClickListener(this);
        clock.setOnClickListener(this);
        search.setOnClickListener(this);
        search.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        consultStatistics.setOnClickListener(this);
    }
}