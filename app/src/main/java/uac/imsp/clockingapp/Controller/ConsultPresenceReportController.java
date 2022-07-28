package uac.imsp.clockingapp.Controller;

import static uac.imsp.clockingapp.Controller.LoginController.CurrentEmployee;

import android.content.Context;

import java.text.ParseException;
import java.util.Hashtable;

import uac.imsp.clockingapp.Models.Day;
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


    public Hashtable < Hashtable<Integer,String>,
            Character>  onMonthSelected(int month) throws ParseException {

        final String [] WEEK_DAYS = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi"};

        Hashtable <Day,Character> report;
        Hashtable <Integer,String> one;
        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultPresenceReportView);
        employeeManager.open();
        report=employeeManager.getPresenceReportForEmployee(employee,month);
        employeeManager.close();


        Hashtable < Hashtable<Integer,String>,
                Character>table = new Hashtable<>();
        one = new Hashtable<>();
Day d;

int n,w,index;
char value;
d=new Day(month,1);
n=d.getLenthOfMonth();
for (index=1;index<=n;index++)
{
  d=new Day(month,index);
  if(d.isWeekEnd())
      continue;;
      w=d.getLenthOfMonth();
      index=d.getDayOfWeek()-1;
      one.put(w,WEEK_DAYS[index]);
      try {
          value = report.getOrDefault(d, 'A');
          table.put(one, value);
      }
      catch (NullPointerException ignored){

      }

}

return table;
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
     return  onMonthSelected(month);

    }
}
