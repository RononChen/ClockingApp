package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.Hashtable;

import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByServiceController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.dao.ServiceManager;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Service;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByServiceView;

public class ConsultStatisticsByServiceController implements
        IConsultStatisticsByServiceController {



    private IConsultStatisticsByServiceView consultStatisticsByServiceView;
    private String startDate,endDate;
    public ConsultStatisticsByServiceController(IConsultStatisticsByServiceView
                                                        consultStatisticsByServiceView)
    {
        this.consultStatisticsByServiceView=consultStatisticsByServiceView;




    }



    @Override
    public String formatDate(int year, int month, int day) {
 Day d=new Day(year,month,day);
        //SQLiteDateFormat : year-month-day

        return  d.getDate();
    }

    @Override
    public void onConsultStatisticsByService() {
        Service [] serviceSet;
        ServiceManager serviceManager;
        serviceManager=new ServiceManager((Context) consultStatisticsByServiceView);
        serviceManager.open();
        serviceSet=serviceManager.getAllService();
        serviceManager.close();
        if(serviceSet.length==0)
            consultStatisticsByServiceView.onNoServiceFound("Aucun service n'a été enregistré");
       else {
           //Ask to select the period or to cancell
            this.consultStatisticsByServiceView.onConsultStatistics("Période", "Sélectionnez la période");


        }
    }


    @Override
    public void onStartDateSelected(int year,int month,int day) {

        //get startDate and ask endDate
   startDate=formatDate( year, month, day);
   consultStatisticsByServiceView.askTime("Sélectionnez la date de fin");

    }
    public void onEndDateSelected(int year,int month,int day) {
        Hashtable <String,Integer> rowSet;
        EmployeeManager employeeManager;

        endDate=formatDate( year, month, day);
        employeeManager =new EmployeeManager((Context) consultStatisticsByServiceView);
        employeeManager.open();
       rowSet= employeeManager.getStatisticsByService(startDate,endDate);
       employeeManager.close();
       consultStatisticsByServiceView.onEndDateSelected(rowSet);

    }

    @Override
    public void onConfirmResult(boolean confirm) {
if(confirm)
    this.consultStatisticsByServiceView.askTime("Sélectionnez la date de début");
    }


}
