package uac.imsp.clockingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private Button register,clock;
    private Intent intent ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        register = findViewById(R.id.menu_register);
        clock=findViewById(R.id.menu_clock);

        register.setOnClickListener(this);
        clock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.menu_register)
            registerEmployee();
        else if(v.getId()==R.id.menu_clock)
            clock();
    }
    public void registerEmployee(){

            intent = new Intent(this, RegisterEmployee.class);
            startActivity(intent);

        }
        public void  clock (){
            intent=new Intent(this, ClockInOut.class);
            startActivity(intent);
        }

}