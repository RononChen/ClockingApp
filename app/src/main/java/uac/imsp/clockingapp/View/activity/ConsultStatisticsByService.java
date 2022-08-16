package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.ConsultStatisticsByServiceController;
import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByServiceController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByServiceView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class ConsultStatisticsByService extends AppCompatActivity
implements IConsultStatisticsByServiceView {
    private IConsultStatisticsByServiceController consultStatisticsByServicePresenter;
    PieChart pieChart;
    private Hashtable<String,Integer> statistics;
    private Toast Toast;
    private int cpt=0;

    DatePickerDialog picker;
    final Calendar cldr = Calendar.getInstance();
    int day = cldr.get(Calendar.DAY_OF_MONTH);
    int month = cldr.get(Calendar.MONTH);
    int year = cldr.get(Calendar.YEAR);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_consult_statistic_by_service);
            pieChart = findViewById(R.id.pieChart_view);
            initPieChart();
            consultStatisticsByServicePresenter=new
                    ConsultStatisticsByServiceController(this);
            consultStatisticsByServicePresenter.onConsultStatisticsByService();



        }

    private void showPieChart(){
            HashMap<String,Integer> resultMap =new HashMap<>();



        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "Statistiques";

        //initializing data
        resultMap.putAll(statistics);
        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

        //input data and fit data into pie chart entry
        for(String service: resultMap.keySet()){
            pieEntries.add(new PieEntry(Objects.requireNonNull(resultMap.get(service)).floatValue(), service));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);

        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    private void initPieChart(){
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);

        //remove the description label on the lower left corner, default true if not set
        //pieChart.getDescription().setEnabled(false);

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#000000"));

    }

    @Override
    public void onConsultStatistics(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Continuer", (dialog, which) -> {




                    consultStatisticsByServicePresenter.onConfirmResult(true);
                })
                .setNegativeButton("Annuler", (dialog, which) -> {

                    Toast = new ToastMessage(ConsultStatisticsByService.this,"Annulé");
                    ConsultStatisticsByService.this.finish();
                    startActivity(getIntent());
                });
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();


    }

    @Override
    public void onNoServiceFound(String message) {
            Toast=new ToastMessage(this,message);
           // finish();


    }


    @Override
    public void askTime(String message) {
            cpt++;
        Toast = new ToastMessage(ConsultStatisticsByService.this,
                message);
        picker = new DatePickerDialog(ConsultStatisticsByService.this,
                (view, year1, monthOfYear, dayOfMonth) -> {

if(cpt==1)
            consultStatisticsByServicePresenter.onStartDateSelected(year1,
                    monthOfYear+1,dayOfMonth);
else if(cpt==2)
{

    consultStatisticsByServicePresenter.onEndDateSelected(year1,
            monthOfYear+1,dayOfMonth);
    cpt=0;

}

                },
                year, month, day);

        picker.show();
    }

    @Override
    public void onEndDateSelected(Hashtable<String, Integer> rowSet) {
            statistics=rowSet;
            showPieChart();
    }
}
