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
   // private String startDate;
    private  Day day;

    public ConsultStatisticsByServiceController(IConsultStatisticsByServiceView
                                                        consultStatisticsByServiceView)
    {
        this.consultStatisticsByServiceView=consultStatisticsByServiceView;
        day=new Day();




    }

    @Override
    public void onConsultStatisticsByService() {
        Hashtable<String,Integer> rowSet;
        EmployeeManager employeeManager;
        employeeManager =new EmployeeManager((Context) consultStatisticsByServiceView);
        employeeManager.open();
        employeeManager.close();
        Service [] serviceSet;
        ServiceManager serviceManager;
        serviceManager=new ServiceManager((Context) consultStatisticsByServiceView);
        serviceManager.open();
        serviceSet=serviceManager.getAllService();
        serviceManager.close();
        if(serviceSet.length==0)
            consultStatisticsByServiceView.onNoServiceFound("Aucun service n'a été enregistré");
       else {
            employeeManager.open();
            rowSet= employeeManager.getStatisticsByService(day.getMonth(),day.getYear());
            consultStatisticsByServiceView.onServiceFound(rowSet);

        }
    }

    @Override
    public void onPreviousMonth() {
        if(day.getDayOfMonth()==1)//we should go to the previous year
            ///moving to the december month of the previous year
            day=new Day(day.getYear()-1,12,1);

        else//[february-december]
            //moving to the previous month of the current year
            day=new Day(day.getYear(),day.getMonth()-1,1);

        onConsultStatisticsByService();



    }

    @Override
    public void onNextMonth() {
        //we'll check the current month
        //if it is december(12)
        if(day.getMonth()==12)
            //we'll move to the january month of the new year
            day=new Day(day.getYear()+1,1,1);
        else
            //we'll move to the next month of the current year
            day=new Day(day.getYear(),day.getMonth()+1,1);
        onConsultStatisticsByService();


    }


    }




