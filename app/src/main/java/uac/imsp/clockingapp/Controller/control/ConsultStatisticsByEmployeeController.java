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
    private IConsultStatisticsByEmployeeView consultStatisticsByEmployeeView;
    public ConsultStatisticsByEmployeeController(IConsultStatisticsByEmployeeView
                                                 consultStatisticsByEmployeeView)
    {
        this.consultStatisticsByEmployeeView=consultStatisticsByEmployeeView;
        day=new Day();

    }

    @Override
    public void onConsultStatisticsForEmployee(int number) {
        String date;
        int n;
        int p=0,a=0,r=0;

        Hashtable <Character,Float> stat;

        stat=new Hashtable<>();
        String[] state;
        date="Le "+day.getFormatedDay()+" "+day.getFormatedMonth()+" "+day.getFormatedYear();;

        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultStatisticsByEmployeeView);
        employeeManager.open();
        employee=new Employee(number);
        state=employeeManager.getPresenceReportForEmployee(employee,day.getMonth(), day.getYear());
        n=state.length;
        for (String str: state)
        {
            if(Objects.equals(str, "Présent"))
                p++;
            else if(str.equals("Absent"))
                a++;
            else if(str.equals("Retard"))
                r++;

        }
        stat.put('P', 10*(float) (p/n));
        stat.put('A', 10*(float) (a/n));
        stat.put('R', 10*(float) (r/n));

        consultStatisticsByEmployeeView.onMonthSelected(stat);



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
