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
        private int rowNum =0;
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
               // colorCurrentDate();


        }

        public void fillRows() {
                boolean test;
                cpt=0;

                int colVar;
                //fill the first row
                tableRow=findViewById(R.id.row1);
                for(colVar =1; colVar <=7; colVar++)
                {
                        rowNum =1;
                        if(colVar <firstDayNumberInWeek)
                                continue;
                        cpt++;
                        ((TextView) tableRow.getChildAt(colVar -1)).setText(String.valueOf(cpt));

                        colorReport(colVar);



                }


              test=  fillRowFrom2to4();

                if(!test) {
                        rowNum =5;


                        tableRow = findViewById(R.id.row5);

                        //row5
                        for (colVar = 1; colVar <= 7; colVar++) {
                                if (cpt == Report.length) {
                                        tableRow = findViewById(R.id.row6);
                                        report.removeView(tableRow);
                                        test=true;
                                        break;
                                }

                                cpt++;
                                ((TextView) tableRow.getChildAt(colVar - 1)).setText(String.valueOf(cpt));
                                 colorReport(colVar);

                        }
                        if(!test)
                        {
                                rowNum =6;
          //row6
                                tableRow = findViewById(R.id.row6);

                                for (colVar = 1; colVar <= 7; colVar++) {
                                          if (cpt == Report.length)
                                                break;
                                          cpt++;
                                        ((TextView) tableRow.getChildAt(colVar - 1)).setText(String.valueOf(cpt));
                                          colorReport(colVar);

                                }

                        }
                }
                fun();

        }

public void fun(){
                Day day=new Day();
                TextView tv;
                int i,j;
                for(i=0;i<6;i++) {
                        tableRow = (TableRow) report.getChildAt(i);
                        for(j=0;j<7;j++) {
                                tv = (TextView) tableRow.getChildAt(j);
                                if(tv.getText().toString().equals(String.valueOf(day.getDayOfMonth())))
                                        //tableRow.getChildAt(j).setBackgroundColor(Color.RED);
                                        tv.setTextColor(Color.rgb(255, 153, 153));

                        }


                }
                cpt=0;


}





        public boolean fillRowFrom2to4() {
                fillRowBetween2And4(2);
                fillRowBetween2And4(3);
              return  fillRowBetween2And4(4);

        }
        public void colorReport(int i){

                if(Objects.equals(Report[cpt - 1], "Présent"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.GREEN);
                else   if(Objects.equals(Report[cpt - 1], "Absent"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.RED);
                else if(Objects.equals(Report[cpt - 1], "Retard"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.YELLOW);
                else if(Objects.equals(Report[cpt - 1], "Hors service"))
                        tableRow.getChildAt(i-1).setBackgroundColor(Color.BLUE);

        }


        public boolean fillRowBetween2And4(int rowNumber){
                int i;
                rowNum =rowNumber;
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
public void findTableRow(){
                switch (rowNum) {
                        case 1:
                                tableRow = findViewById(R.id.row1);
                                break;
                        case 2:
                                tableRow = findViewById(R.id.row2);
                                break;
                        case 3:
                                tableRow = findViewById(R.id.row3);
                                break;
                        case 4:
                                tableRow = findViewById(R.id.row4);
                                break;
                        case 5:
                                tableRow = findViewById(R.id.row5);
                                break;
                        case 6:
                                tableRow = findViewById(R.id.row6);
                                break;
                        default:
                                break;
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