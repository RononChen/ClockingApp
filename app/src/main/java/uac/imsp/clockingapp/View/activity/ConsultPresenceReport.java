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
        private TableRow tableRow;
        private TextView Date;
        private  Button previous,next;
        private  int cpt=0;
        private int firstDayNumberInWeek;
        private  int count=0;
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
                fillRows();
                colorCurrentDate();


        }

        public void fillRows() {
                boolean test;
                cpt=0;

                int i;
                //fill the first row
                tableRow=findViewById(R.id.row1);
                for(i=1;i<=7;i++)
                {
                        if(i<firstDayNumberInWeek)
                                continue;
                        cpt++;
                        ((TextView) tableRow.getChildAt(i-1)).setText(String.valueOf(cpt));

                        colorReport(i);



                }


              test=  fillRowFrom2to4();

                if(!test) {


                        tableRow = findViewById(R.id.row5);

                        //row5
                        for (i = 1; i <= 7; i++) {
                                if (cpt == Report.length) {
                                        tableRow = findViewById(R.id.row6);
                                        report.removeView(tableRow);
                                        test=true;
                                        break;
                                }

                                cpt++;
                                ((TextView) tableRow.getChildAt(i - 1)).setText(String.valueOf(cpt));
                                 colorReport(i);

                        }
                        if(!test)
                        {
          //row6
                                tableRow = findViewById(R.id.row6);

                                for (i = 1; i <= 7; i++) {
                                         if (cpt == Report.length)
                                                break;

                                             cpt++;
                                        ((TextView) tableRow.getChildAt(i - 1)).setText(String.valueOf(cpt));
                                          colorReport(i);

                                }

                        }
                }

        }







        public boolean fillRowFrom2to4() {
                fillRowBetween2And4(2);
                fillRowBetween2And4(3);
              return  fillRowBetween2And4(4);

        }
        public void colorReport(int i){
                Day day=new Day();
                if(Objects.equals(Report[cpt - 1], "Présent"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.GREEN);
                else   if(Objects.equals(Report[cpt - 1], "Absent"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.RED);
                else if(Objects.equals(Report[cpt - 1], "Retard"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.YELLOW);
                else if(Objects.equals(Report[cpt - 1], "Hors service"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.BLUE);




        }
        public void colorCurrentDate(){
                int i;
                int j = -1;
                Day day=new Day();
                for(i=0;i<Report.length;i++)
                        if(count==0&&i+1==day.getDayOfMonth())
                        {
                                if(0<=i&&i<7)
                                        j=0;
                                else  if(7<=i&&i<14)
                                        j=1;
                                else  if(14<=i&&i<21)
                                        j=3;
                                else  if(21<=i&&i<28)
                                        j=4;
                                else  if(28<=i&&i<35)
                                        j=5;
                                ((TextView)tableRow.getChildAt(j)).setTextColor(Color.WHITE);
                                 count++;
                                break;
                        }
        }

        public boolean fillRowBetween2And4(int rowNumber){
                int i;
                switch (rowNumber){
                        case 2:
                                tableRow=findViewById(R.id.row2);
                                break;
                        case 3:
                                tableRow=findViewById(R.id.row3);
                                break;
                        case 4:
                                tableRow=findViewById(R.id.row4);
                                break;
                        default:
                                break;

                }
                for(i=1;i<=7;i++)
                {
                        if(cpt==Report.length) {
                                tableRow = findViewById(R.id.row5);
                                report.removeView(tableRow);
                                tableRow = findViewById(R.id.row6);
                                report.removeView(tableRow);

                                return true;
                        }
                        cpt++;
                        ((TextView) tableRow.getChildAt(i-1)).setText(String.valueOf(cpt));

                }

return false;

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