package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.Objects;

import uac.imsp.clockingapp.Controller.util.IStartScreenController;
import uac.imsp.clockingapp.Models.dao.ClockingManager;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IStartScreenView;

public class StartScreenController  implements IStartScreenController {
    private final IStartScreenView startScreenView;
    public StartScreenController(IStartScreenView startScreenView){
        this.startScreenView=startScreenView;
        updateDailyAttendance();

    }


    @Override
    public void onLogin() {
        startScreenView.onLogin();


    }

    @Override
    public void onClocking() {
        startScreenView.onClocking();

    }

    @Override
    public void onLoad(int savedVersionCode, int currentVersionCode) {
        final int DOESNT_EXIST=-1;

        if(savedVersionCode==currentVersionCode)
            startScreenView.onNormalRun();

        else if (savedVersionCode==DOESNT_EXIST)
        {


            startScreenView.onFirstRun();
        }
        else if (savedVersionCode < currentVersionCode )
            startScreenView.onUpgrade();





    }

    /**This method update the daily attendance of  all employees at the first lunch of the
     * application each day.**/
    public void updateDailyAttendance(){
        String lastUpdate;
        Day day=new Day();
        String status;
        EmployeeManager employeeManager=new EmployeeManager((Context) startScreenView);
        employeeManager.open();

        ClockingManager clockingManager=new ClockingManager((Context) startScreenView);
        lastUpdate =employeeManager.selectVariable();
        clockingManager.open();
        //We get the whole list of employees
        Employee[] employees=employeeManager.search("*");
        /*if the date of last update is not the date of the current day:in other ways
         if the employees daily attendence is not already updated the current day
         */
        if(!Objects.equals(lastUpdate, day.getDate()))
           for(Employee employee:employees)
           {
               if(employeeManager.shouldNotWorkToday(employee))
                  status="Hors service";
               else
                  status="Absent";
                employeeManager.setDayAttendance(employee,status);

        }
        employeeManager.updateVariable();
    }
}
