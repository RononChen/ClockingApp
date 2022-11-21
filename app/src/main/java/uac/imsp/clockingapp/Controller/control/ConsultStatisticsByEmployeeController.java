package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.text.ParseException;
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
    private IConsultStatisticsByEmployeeView consultStatisticsByEmployeeView;
    public ConsultStatisticsByEmployeeController(IConsultStatisticsByEmployeeView
                                                 consultStatisticsByEmployeeView)
    {
        this.consultStatisticsByEmployeeView=consultStatisticsByEmployeeView;

    }

    @Override
    public void onConsultStatisticsForEmployee(int number) {
        employee = new Employee(number);
        consultStatisticsByEmployeeView.onStart("Sélectionnez le mois");


    }


    @Override
    public void onMonthSelected(int month) throws ParseException {
        int n;
        int p=0,a=0,hs=0,w=0,r=0;
        Day day;
        Hashtable <Character,Float> stat;

        stat=new Hashtable<>();
        String[] state;
        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager((Context) consultStatisticsByEmployeeView);
        employeeManager.open();
        state=employeeManager.getPresenceReportForEmployee(employee,month);
        employeeManager.close();
        //day=new Day();
        n=state.length;
        for (String str: state
             )
        {
            if(Objects.equals(str, "Présent"))
                p++;
            else if(str.equals("Absent"))
                a++;
            else if(str.equals("Retard"))
                r++;
            else if(str.equals("Hors service"))
                hs++;
           /* else if(ch=='W')
                w++;*/

        }
        stat.put('P', 10*(float) (p/n));
        stat.put('A', 10*(float) (a/n));
        stat.put('R', 10*(float) (r/n));
        stat.put('H', 10*(float) (hs/n));

        consultStatisticsByEmployeeView.onMonthSelected(stat);


    }
}
