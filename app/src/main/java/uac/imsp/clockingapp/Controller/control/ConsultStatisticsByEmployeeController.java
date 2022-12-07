package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.util.IConsultStatisticsByEmployeeController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IConsultStatisticsByEmployeeView;

public class ConsultStatisticsByEmployeeController implements
        IConsultStatisticsByEmployeeController {
    private Employee employee;
    private  Day day;
    private final Day currentDay;
    int cpt=0;
    private final IConsultStatisticsByEmployeeView consultStatisticsByEmployeeView;
    public ConsultStatisticsByEmployeeController(IConsultStatisticsByEmployeeView
                                                 consultStatisticsByEmployeeView)
    {
        this.consultStatisticsByEmployeeView=consultStatisticsByEmployeeView;
        day=new Day();
        currentDay=day;

    }

    @Override
    public void onConsultStatisticsForEmployee(int number) {

        int total=0;
        boolean accessible =true;

        int p=0,a=0,r=0;
        Day d;
        Hashtable <Character,Float> stat;

        stat=new Hashtable<>();
        String[] state;

        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultStatisticsByEmployeeView);
        employeeManager.open();
        employee=new Employee(number);
        employeeManager.retrieveAddDate(employee);
        //the month following the current one
        d=new Day();


        if (day.addMonth().getMonthPart().compareTo(currentDay.getMonthPart()) > 0) {
            consultStatisticsByEmployeeView.onReportNotAccessible(true);
            accessible =false;
        }
        else
            consultStatisticsByEmployeeView.onReportAccessible(true);



        //the month preceding the current one
        if (day.subtractMonth().getMonthPart().
                compareTo(new Day(employee.getAddDate()).getMonthPart()) < 0) {
            consultStatisticsByEmployeeView.onReportNotAccessible(false);
            accessible =false;
        }
        else
            consultStatisticsByEmployeeView.onReportAccessible(false);
        if(accessible|| this.cpt ==0) {
            consultStatisticsByEmployeeView.onStart(day.getDayOfWeek(), d.getDayOfWeek(),
                    d.getFirstDayOfMonth(), day.getMonth(), day.getYear());
            state = employeeManager.getPresenceReportForEmployee(employee, day.getMonth(), day.getYear());
            //n = state.length;
           for(int i=0;i< state.length;i++) {
               if (i == day.getDayOfMonth())
                   break;
               if(!Objects.equals(state[i], "Hors service") && !Objects.equals(state[i],"Undefined"))
                  total++;
           }




            for (String str : state) {
                if (Objects.equals(str, "Présent"))
                    p++;
                else if (str.equals("Absent"))
                    a++;
                else if (str.equals("Retard"))
                    r++;

            }
            stat.put('P', 100*(float)p/ total);
            stat.put('A', 100*(float)a/ total);
            stat.put('R',100*(float) r/ total);



            consultStatisticsByEmployeeView.onMonthSelected(stat);

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

        onConsultStatisticsForEmployee(employee.getRegistrationNumber());

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
        onConsultStatisticsForEmployee(employee.getRegistrationNumber());

    }
}
