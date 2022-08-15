package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Controller.control.LoginController.CurrentEmployee;

import android.content.Context;

import java.text.ParseException;
import java.util.Hashtable;

import uac.imsp.clockingapp.Controller.util.IConsultPresenceReportController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IConsultPresenceReportView;

public class ConsultPresenceReportController implements IConsultPresenceReportController {
    private IConsultPresenceReportView consultPresenceReportView;
    private Employee employee;
    public ConsultPresenceReportController(IConsultPresenceReportView consultPresenceReportView){

        this.consultPresenceReportView=consultPresenceReportView;
    }

    @Override
    public void onConsultPresenceReport(int number) {
        employee = new Employee(number);
        consultPresenceReportView.onStart("Sélectionnez le mois");
        
    }


    public void onMonthSelected(int month) throws ParseException {

        Day day;
int M = month;
        Character [] state;
        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultPresenceReportView);
        employeeManager.open();
        state=employeeManager.getPresenceReportForEmployee(employee,month);

        employeeManager.close();
                day=new Day();

           consultPresenceReportView.onMonthSelected(state,day.getDayOfWeek());
    }

    @Override
    public void onConsultOwnStatistics()   {

        onConsultPresenceReport(CurrentEmployee);
    }

    @Override
    public Hashtable < Hashtable<Integer,String>,
            Character>  onOwnMonthSelected() throws ParseException {

     int month=0;
     onMonthSelected(month);
     return  null;

    }
}
