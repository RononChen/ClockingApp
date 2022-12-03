/*package uac.imsp.clockingapp.View.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import uac.imsp.clockingapp.R;

public class  ConsultPresenceReport extends Activity{


        EditText txt1;
        EditText txt2;
        int Row;
        int Col;
        int count, i, j;
        String str;
        String stm;
        Button Create;
        TableLayout TabLayout_Create;
        TableLayout TabLayout_Show;
        Button Show;
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_presence_report2);

        txt1 = (EditText) findViewById(R.id.editText1);
        txt2 = (EditText) findViewById(R.id.editText2);
        Create = (Button) findViewById(R.id.button1);
        Show = (Button) findViewById(R.id.Show);
        TabLayout_Create = (TableLayout) findViewById(R.id.TableLayout);
        TabLayout_Show = (TableLayout) findViewById(R.id.TableLayout2);
        Create.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {


import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;str = txt1.getText().toString();
        stm = txt2.getText().toString();

        Row = Integer.parseInt(str);
        Col = Row = Integer.parseInt(stm);

        Toast.makeText(ConsultPresenceReport.this,
        str, Toast.LENGTH_SHORT).show();
        Toast.makeText(ConsultPresenceReport.this,
        stm, Toast.LENGTH_SHORT).show();


        // TextView[] txt;

        for (i = 1; i <= Row; i++) {
final TableRow row = new TableRow(ConsultPresenceReport.this);
        if (i%2 == 0) {
        row.setBackgroundColor(Color.MAGENTA);
        } else {
        row.setBackgroundColor(Color.GRAY);
        }

        for (j = 1; j <= Col; j++) {

final EditText txt = new EditText(ConsultPresenceReport.this);
        txt.setTextColor(Color.GREEN);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
        txt.setTypeface(Typeface.SERIF, Typeface.BOLD);
        txt.setGravity(Gravity.LEFT);
        txt.setGravity(Gravity.LEFT);
        txt.setText("C" + i + j + " ");

        row.addView(txt);
        }
        TabLayout_Create.addView(row);

        }


        }
        });

        Show.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {
        // TODO Auto-generated method stub

        for (i = 0; i < Row; i++) {
final TableRow row = (TableRow) TabLayout_Create.getChildAt(i);
final TableRow row1 = new TableRow(ConsultPresenceReport.this);

        if (i % 2 == 0) {
        row1.setBackgroundColor(Color.YELLOW);
        } else {
        row1.setBackgroundColor(Color.RED);
        }
        for (j = 0; j < Col; j++) {
final EditText etxt = (EditText) row.getChildAt(j);

final TextView txt = new TextView(ConsultPresenceReport.this);
        txt.setTextColor(Color.GREEN);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
        txt.setTypeface(Typeface.SERIF, Typeface.BOLD);
        txt.setGravity(Gravity.LEFT);
        txt.setText(etxt.getText());
        row1.addView(txt);
        }
        TabLayout_Show.addView(row1);
        }

        }
        });

        }

        }

*/
        package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.ConsultPresenceReportController;
import uac.imsp.clockingapp.Controller.util.IConsultPresenceReportController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IConsultPresenceReportView;

public class ConsultPresenceReport extends AppCompatActivity
        implements IConsultPresenceReportView,
        View.OnClickListener {
        private TableLayout report;
        private TableRow tableRow;
        private TextView textView;
        private TextView Date;
        private  Button previous,next;

        IConsultPresenceReportController consultPresenceReportPresenter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_consult_presence_report2);

                consultPresenceReportPresenter=
                        new ConsultPresenceReportController
                                (this);
                initView();

        }
        public void initView(){
                String[] strings;
                int i;
                previous = findViewById(R.id.report_previous);
                next = findViewById(R.id.report_next);
                previous.setOnClickListener(this);
                next.setOnClickListener(this);
                next.setOnClickListener(this);

                report =findViewById(R.id.report_table);
                Date=findViewById(R.id.report_date);

                int actionNumber = getIntent().getIntExtra("CURRENT_NUMBER", 1);
                 strings = new String[]{"Lundi", "Mardi", "Mercredi",
                        "Jeudi", "Vendredi"};


                tableRow=new TableRow(ConsultPresenceReport.this);
                for(i=0;i<5;i++)
                {
                        textView=new TextView(this);
                        textView.setGravity(Gravity.START);
                        textView.setText(strings[i]);
                        tableRow.addView(textView);


                }
                report.addView(tableRow);
                consultPresenceReportPresenter.onConsultPresenceReport(actionNumber);





        }

        @Override
        public void onStart(String date) {

                Date.setText(date);
        }
  /**
   This function takes as arguments :
   1-the report which is a table of strings that contains the presence state
   of employee during the concerned month
   2-the firstDayNumberInWeek which the number (from 1 to 7) of the the
   firstday in the current month (as a day of the first week)
   **/
        @Override
        public void onMonthSelected(String[] report, int firstDayNumberInWeek) {

String text;
               this.report=findViewById(R.id.report_table);
           int i,j,cpt=0;
           for (i=1;i<=5;i++)
           {

                   if(cpt==report.length)
                           break;
                   tableRow=new TableRow(ConsultPresenceReport.this);
                   for (j=1;j<=5;j++)
                   {

                           textView=new TextView(this);
                           textView.setGravity(Gravity.START);
                           if (i == 1 && j < firstDayNumberInWeek) {
                                   text = "";
                                   //test=false;
                           }
                           else {
                                   text = String.valueOf(report[cpt]);

                           }
                           textView.setText(text);
                           tableRow.addView(textView);

                                   cpt++; //increment the number of filled cases

                   }
                   this.report.addView(tableRow);

           }




        }

        @Override
        public void onReportError(boolean nextError) {
                if(nextError)
                        next.setClickable(false);
                else
                        previous.setClickable(true);

        }

        @Override
        public void onClick(View v) {
                if(v.getId()==R.id.report_previous)
                        consultPresenceReportPresenter.onPreviousMonth();
                else if (v.getId()==R.id.report_next)
                        consultPresenceReportPresenter.onNextMonth();
        }
}