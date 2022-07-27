package uac.imsp.clockingapp.Controller;

import android.content.Context;

import java.util.Hashtable;

import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.Models.Service;
import uac.imsp.clockingapp.Models.ServiceManager;
import uac.imsp.clockingapp.View.IConsultStatisticsView;

public class ConsultStatisticsController implements IConsultStatisticsController {

    private IConsultStatisticsView consultStatisticsView;
    private String startDate,endDate;
    public ConsultStatisticsController(IConsultStatisticsView consultStatisticsView){
        this.consultStatisticsView=consultStatisticsView;
        consultStatisticsView.onConsultStatistics("Choix du type de statistique",
                "Voulez vous consulter par service ou par employé ?");

    }



    @Override
    public String formatDate(int year, int month, int day) {

        //SQLiteDateFormat : year-month-day
        return  year+"-"+month+"-"+day;
    }

    @Override
    public void onConsultStatisticsByService() {
        int i;
        Service [] serviceSet;
        String[] serviceName;
        ServiceManager serviceManager;
        serviceManager=new ServiceManager((Context) consultStatisticsView);
        serviceManager.open();
        serviceSet=serviceManager.getAllService();
        serviceManager.close();
        if(serviceSet.length==0)
            consultStatisticsView.onNoServiceFound("Aucun service n'a été enregistré");
        else
        {
            serviceName=new String[serviceSet.length];
            for (i=0;i<serviceSet.length;i++)
                serviceName[i]=serviceSet[i].getName();


            // we give serviceName list to the View
            consultStatisticsView.onServiceFound(serviceName);
        }



    }

    @Override
    public void onServiceSelected(String serviceName) {
        consultStatisticsView.onServiceSelected("Période","Entrez la période");
        consultStatisticsView.askTime("Date de Début");

    }

    @Override
    public void onStartDateSelected(int year,int month,int day) {

   startDate=formatDate( year, month, day);
   consultStatisticsView.askTime("Date de fin");

    }
    public void onEndDateSelected(int year,int month,int day) {
        Hashtable <String,Double> rowSet;
        EmployeeManager employeeManager;

        endDate=formatDate( year, month, day);
        employeeManager =new EmployeeManager((Context) consultStatisticsView);
        employeeManager.open();
       rowSet= employeeManager.getStatisticsByService(startDate,endDate);
       employeeManager.close();
       consultStatisticsView.onEndDateSelected(rowSet);

    }

    @Override
    public void OnEmployeeSelected(int number) {
        int month=-1;
        Employee employee = new Employee(number);
        consultStatisticsView.onEmployeeSelected("Sélectionnez le mois",month);

    }
}
