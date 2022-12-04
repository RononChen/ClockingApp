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
    private final Context context;
    //private int Month,Year;
    private Day day;
    private final Day currentDay;
    int cpt=0;

    public ConsultPresenceReportController(IConsultPresenceReportView consultPresenceReportView){

        this.consultPresenceReportView=consultPresenceReportView;
        this.context=(Context) consultPresenceReportView;
         day =new Day();
         currentDay=day;


    }

    @Override
    public void onConsultPresenceReport(int number) {
        String period;
        String [] state;
        EmployeeManager employeeManager;
        boolean accessible =true;
        employee=new Employee(1);
        employeeManager = new EmployeeManager(context);
        employeeManager.open();
        employeeManager.retrieveAddDate(employee);

        if (day.addMonth().getMonthPart().compareTo(currentDay.getMonthPart()) > 0) {
            consultPresenceReportView.onReportError(true);
            accessible =false;
        }
        if (day.subtractMonth().getMonthPart().
                compareTo(new Day(employee.getAddDate()).getMonthPart()) < 0) {
            consultPresenceReportView.onReportError(false);
            accessible =false;
        }
        period = "Du 01 " + day.getFormatedMonth() + " " + day.getFormatedYear() +
                " au " +
                day.getLenthOfMonth() + " " + day.getFormatedMonth() + " " + day.getFormatedYear();
if(accessible||cpt==0) {
    consultPresenceReportView.onStart(period);



    state = employeeManager.getPresenceReportForEmployee(employee, day.getMonth(), day.getYear());

    consultPresenceReportView.onMonthSelected(state, day.getFirstDayOfMonth());

}
        
    }

    @Override
    public void onPreviousMonth() {
        //Employee employee;

                   if (day.getDayOfMonth() == 1)//we should go to the previous year
                ///moving to the december month of the previous year
                day = new Day(day.getYear() - 1, 12, 1);

            else//[february-december]
                //moving to the previous month of the current year
                day = new Day(day.getYear(), day.getMonth() - 1, 1);

            onConsultPresenceReport(employee.getRegistrationNumber());

          }

    @Override
    public void onNextMonth() {


        // we'll check if the next month is passed
       //

            //we'll check the current month
            //if it is december(12)

            if (day.getMonth() == 12)
                //we'll move to the january month of the new year
                day = new Day(day.getYear() + 1, 1, 1);
            else
                //we'll move to the next month of the current year
                day = new Day(day.getYear(), day.getMonth() + 1, 1);
            onConsultPresenceReport(employee.getRegistrationNumber());




    }

    @Override
    public void onConsultOwnStatistics()   {

        onConsultPresenceReport(CurrentEmployee);
    }


}
