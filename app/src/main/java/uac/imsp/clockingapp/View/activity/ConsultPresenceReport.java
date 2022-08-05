package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.ConsultPresenceReportController;
import uac.imsp.clockingapp.Controller.util.IConsultPresenceReportController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IConsultPresenceReportView;

public class ConsultPresenceReport extends AppCompatActivity
        implements IConsultPresenceReportView
{
    IConsultPresenceReportController consultPesenceRapportPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_presence_report2);

        consultPesenceRapportPresenter=new
                ConsultPresenceReportController(this);
    }

    @Override
    public void onEmployeeSelected(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}