package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;

import uac.imsp.clockingapp.Controller.control.ConsultStatisticsByEmployeeController;
import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByEmployeeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByEmployeeView;

public class ConsultStatisticsByEmployee extends AppCompatActivity
 implements IConsultStatisticsByEmployeeView , DialogInterface.OnClickListener {
    private IConsultStatisticsByEmployeeController consultStatisticsByEmployeePresenter;
    private int actionNumber;
private Hashtable <Character, Float> statistics;

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_statistics_by_employee);
        barChart = findViewById(R.id.chart);

        actionNumber=getIntent().getIntExtra("ACTION_NUMBER",1);
        consultStatisticsByEmployeePresenter=new ConsultStatisticsByEmployeeController(this);

        consultStatisticsByEmployeePresenter.onConsultStatisticsForEmployee(actionNumber);
    }

    @Override
    public void onStart(String message) {
        String [] months=getResources().getStringArray(R.array.months);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(message);
        builder.setItems(months,this);
        AlertDialog dialog = builder.create();

        dialog.show();


    }

    @Override
    public void onMonthSelected(Hashtable<Character, Float> statistics) {
        this.statistics=statistics;


        // calling method to get bar entries.
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();
        // if(statistics.containsKey('P'))

        barEntriesArrayList.add(new BarEntry(1f, statistics.get('P')));
        barEntriesArrayList.add(new BarEntry(2f,  statistics.get('A')));
        barEntriesArrayList.add(new BarEntry(3f, statistics.get('R')));
        barEntriesArrayList.add(new BarEntry(4f, statistics.get('W')));
        barEntriesArrayList.add(new BarEntry(5f, statistics.get('F')));


        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Statistiques" +
                " de présence par employé");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        try {
            consultStatisticsByEmployeePresenter.onMonthSelected(which);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}