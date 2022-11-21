package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Controller.control.LoginController.CurrentEmployee;

import android.content.Context;

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
        consultPresenceReportView.onStart("");


        Day day=new Day();
        int month= day.getMonth();

        String [] state=null;
        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultPresenceReportView);
        employeeManager.open();
        state=employeeManager.getPresenceReportForEmployee(employee,month);


        //employeeManager.close();
       // day=new Day();


        consultPresenceReportView.onMonthSelected(state,day.getFirstDayOfMonth());
        
    }


    public void onMonthSelected(int month)  {

        Day day;

        String[] state;
        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultPresenceReportView);
        employeeManager.open();
        state=employeeManager.getPresenceReportForEmployee(employee,month);


        //employeeManager.close();
                day=new Day();


           consultPresenceReportView.onMonthSelected(state,day.getFirstDayOfMonth());
    }

    @Override
    public void onConsultOwnStatistics()   {

        onConsultPresenceReport(CurrentEmployee);
    }

    @Override
    public Hashtable < Hashtable<Integer,String>,
            Character>  onOwnMonthSelected()  {

     int month=0;

            onMonthSelected(month);

        return  null;

    }
}
