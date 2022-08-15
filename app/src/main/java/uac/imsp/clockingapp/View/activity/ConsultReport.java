package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;

public class ConsultReport extends AppCompatActivity {
    private TableLayout report;
    private TableRow tableRow;
    private TextView textView;
    private String[] strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_report);
        initView();
    }
    public void initView(){
        strings=new String[]{"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};
        int i;
        report =findViewById(R.id.report_table);
        tableRow=new TableRow(ConsultReport.this);
        for(i=0;i<6;i++)
        {
            textView=new TextView(this);
            textView.setText(strings[i]);

        }


    }

}