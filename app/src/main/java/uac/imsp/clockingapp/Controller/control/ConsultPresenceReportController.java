package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Controller.control.LoginController.CurrentEmployee;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.IConsultPresenceReportController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IConsultPresenceReportView;

public class ConsultPresenceReportController implements IConsultPresenceReportController {
    private IConsultPresenceReportView consultPresenceReportView;
    private Employee employee;
    //private int Month,Year;
    private Day day;

    public ConsultPresenceReportController(IConsultPresenceReportView consultPresenceReportView){

        this.consultPresenceReportView=consultPresenceReportView;
         day =new Day();

    }

    @Override
    public void onConsultPresenceReport(int number) {
        String date;
        String [] state;
        EmployeeManager employeeManager;
        date="Le "+day.getFormatedDay()+" "+day.getFormatedMonth()+" "+day.getFormatedYear();
        employee = new Employee(number);
        consultPresenceReportView.onStart(date);

        employeeManager=new EmployeeManager((Context) consultPresenceReportView);
        employeeManager.open();

        state=employeeManager.getPresenceReportForEmployee(employee,day.getMonth(),day.getYear());

        consultPresenceReportView.onMonthSelected(state,day.getFirstDayOfMonth());
        
    }

    @Override
    public void onPreviousMonth() {

        if(day.getDayOfMonth()==1)//we should go to the previous year
            ///moving to the december month of the previous year
            day=new Day(day.getYear()-1,12,1);

        else//[february-december]
            //moving to the previous month of the current year
           day=new Day(day.getYear(),day.getMonth()-1,1);

        onConsultPresenceReport(employee.getRegistrationNumber());
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
        onConsultPresenceReport(employee.getRegistrationNumber());

    }

    @Override
    public void onConsultOwnStatistics()   {

        onConsultPresenceReport(CurrentEmployee);
    }


}
