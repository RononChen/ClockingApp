        package uac.imsp.clockingapp.View.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.ConsultPresenceReportController;
import uac.imsp.clockingapp.Controller.util.IConsultPresenceReportController;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IConsultPresenceReportView;

public class ConsultPresenceReport extends AppCompatActivity
        implements IConsultPresenceReportView,
        View.OnClickListener {
        private TableLayout report;
        private TextView Date;
        private  Button previous,next;
        private int firstDayNumberInWeek;
 private String[] Report;
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
                previous = findViewById(R.id.report_previous);
                next = findViewById(R.id.report_next);
                previous.setOnClickListener(this);
                next.setOnClickListener(this);
                next.setOnClickListener(this);
                report =findViewById(R.id.report_table);
                Date=findViewById(R.id.report_date);

                int actionNumber = getIntent().getIntExtra("CURRENT_NUMBER", 1);

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

                Report=report;
                this.firstDayNumberInWeek=firstDayNumberInWeek;
                setReportVisible();
        }



        public void setReportVisible(){
                TableRow tableRow;
                boolean allBrowsed=false;
                Day day=new Day();
                TextView textView;
                int i,j,c=-1;
                for(i=1;i<=6;i++)
                {


                        if(allBrowsed)
                        {
                                for(c=i;c<=6;c++) {
                                        tableRow = (TableRow) report.getChildAt(c);
                                        report.removeView(tableRow);
                                }
                                break;
                        }

                        tableRow = (TableRow) report.getChildAt(i);
                        for(j=1;j<=7;j++) {
                                if (i == 1 && j < firstDayNumberInWeek)
                                        continue;

                                c++;
                                //all days are browsed
                                if(c==Report.length) {
                                        allBrowsed=true;
                                        /*for(c=j-1;c<7;c++)
                                        {
                                            textView= (TextView) tableRow.getChildAt(c);
                                           textView.setVisibility(View.GONE);
                                        }*/

                                        break;
                                }

                                textView=((TextView) (tableRow.getChildAt(j - 1)));
                                textView.setText(String.valueOf(c+1));
                                //mark the current date
                                if(c+1==day.getDayOfMonth())
                                        textView.setTextColor(Color.rgb(15,99,66));

                                if(Objects.equals(Report[c], "Présent"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.GREEN);
                                else   if(Objects.equals(Report[c], "Absent"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.RED);
                                else if(Objects.equals(Report[c], "Retard"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.YELLOW);
                                else if(Objects.equals(Report[c], "Hors service"))
                                        tableRow.getChildAt(j-1).setBackgroundColor(Color.BLUE);
                        }

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