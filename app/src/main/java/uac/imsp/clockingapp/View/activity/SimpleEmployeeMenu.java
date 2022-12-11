package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.SimpleEmployeeMenuController;
import uac.imsp.clockingapp.Controller.util.ISimpleEmployeeMenuController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ISimpleEmployeeMenuView;

public class SimpleEmployeeMenu extends AppCompatActivity
        implements ISimpleEmployeeMenuView {
    ISimpleEmployeeMenuController simpleEmployeeMenuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_employee_menu);
        simpleEmployeeMenuPresenter =
                new SimpleEmployeeMenuController(this);


    }

    @Override
    public void onConsultatisticsMenuSuccessful() {

    }

    @Override
    public void onClocking() {

    }

    @Override
    public void onConsultPresenceReport() {

    }

    @Override
    public void onExit() {

    }
}