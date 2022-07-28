package uac.imsp.clockingapp.Controller;

import android.content.Context;

import java.util.Hashtable;

import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.View.IConsultPresenceReportView;

public class ConsultPresenceReportController implements  IConsultPresenceReportController{
    private IConsultPresenceReportView consultPresenceReportView;
    private Employee employee;
    public ConsultPresenceReportController(IConsultPresenceReportView consultPresenceReportView){

        this.consultPresenceReportView=consultPresenceReportView;
    }

    @Override
    public void onConsultPresenceReport(int number) {
        employee = new Employee(number);
        consultPresenceReportView.onEmployeeSelected("Sélectionnez le mois");
    }

    @Override
    public  Hashtable <String ,Character> onMonthSelected(int month) {


                Hashtable <String,Character> report;
        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultPresenceReportView);
        employeeManager.open();
        report=employeeManager.getPresenceReportForEmployee(employee,month);
        employeeManager.close();
        return  report;
    }
}
