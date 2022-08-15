package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.text.ParseException;
import java.util.Hashtable;

import uac.imsp.clockingapp.Controller.util.IConsultStatisticsController;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Service;
import uac.imsp.clockingapp.Models.dao.ServiceManager;
import uac.imsp.clockingapp.View.util.IConsultStatisticsView;

public class ConsultStatisticsController implements IConsultStatisticsController {


    private IConsultStatisticsView consultStatisticsView;
    private String startDate,endDate;
    private Employee employee;
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
        employee = new Employee(number);

        consultStatisticsView.onEmployeeSelected("Sélectionnez le mois",month);



    }

    @Override
    public void onMonthSelected(int month, Hashtable<Day, Character> report) throws ParseException {
        EmployeeManager employeeManager;
        employeeManager = new EmployeeManager((Context) consultStatisticsView);
        employeeManager.open();

        //noinspection UnusedAssignment
       // report=employeeManager.getPresenceReportForEmployee(employee,month);
        employeeManager.close();


    }


}
