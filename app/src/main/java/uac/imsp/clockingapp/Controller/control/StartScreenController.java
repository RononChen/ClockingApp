package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.Objects;

import dao.DayManager;
import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.IStartScreenController;
import uac.imsp.clockingapp.View.util.IStartScreenView;

public class StartScreenController  implements IStartScreenController {
    private final IStartScreenView startScreenView;
    private final Context context;
    Runnable runnable;

    public StartScreenController(IStartScreenView startScreenView){
        this.startScreenView=startScreenView;
        context=(Context) this.startScreenView;

        runnable= this::updateDailyAttendance;
        AsyncTask.execute(runnable);




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
        final String PREFS_NAME="MyPrefsFile";
        final int DOESNT_EXIST=-1;
        SharedPreferences preferences= context.getSharedPreferences(PREFS_NAME,0);

        if(savedVersionCode==currentVersionCode)
        {

            if(preferences.getString("nextStep","").equals("none"))
            startScreenView.onNormalRun();
            else if(preferences.getString("nextStep","").equals("account"))
                startScreenView.onAccount();
            else if(preferences.getString("nextStep","").equals("service"))
                startScreenView.onService();
            else if(preferences.getString("nextStep","").equals("setup"))
             startScreenView.onSetUp();
        }



        else if (savedVersionCode==DOESNT_EXIST)
        {

            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("entrepriseName","");
            editor.putString("entrepriseEmail","");
            editor.putString("entrepriseDescription","");
            //For userDoc
            editor.putString("userDoc","");

            editor.putBoolean("emailAsUsername",false);
            editor.putBoolean("editUsername",true);
            editor.putBoolean("generatePassword",false);
            editor.putBoolean("showPasswordDuringAdd",false);
            //For others settings
            editor.putBoolean("darkMode",false);
            editor.putString("language","Français");
            editor.putBoolean("notifyAdd",true);
            editor.putBoolean("notifyDelete",false);
            editor.putBoolean("notifyUpdate",false);
            editor.putBoolean("useQRCode",true);
            editor.putString("lang","fr");
            editor.putBoolean("dark",false);
            editor.putString("nextStep","setup");
            editor.apply();
            startScreenView.onFirstRun();
        }
        else if (savedVersionCode < currentVersionCode )
            startScreenView.onUpgrade();
        else
            startScreenView.onDowngrade();






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
