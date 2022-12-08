package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.Objects;

import dao.DayManager;
import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IStartScreenController;
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
        DayManager dayManager=new DayManager((Context) startScreenView);
        dayManager.open();
        dayManager.create(day);
        dayManager.close();



        EmployeeManager employeeManager=new EmployeeManager((Context) startScreenView);
        employeeManager.open();
        lastUpdate =employeeManager.selectVariable();
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
                employeeManager.setDayAttendance(employee,status,day);

        }
        employeeManager.updateVariable();
        employeeManager.close();
    }
}
