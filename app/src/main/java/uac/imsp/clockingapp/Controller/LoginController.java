package uac.imsp.clockingapp.Controller;

import static uac.imsp.clockingapp.Models.Employee.EMPTY_PASSWORD;
import static uac.imsp.clockingapp.Models.Employee.EMPTY_USERNAME;
import static uac.imsp.clockingapp.Models.Employee.INVALID_PASSWORD;
import static uac.imsp.clockingapp.Models.Employee.INVALID_USERNAME;
import static uac.imsp.clockingapp.Models.EmployeeManager.CAN_NOT_LOGIN;

import android.content.Context;

import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.View.ILoginView;

public class LoginController  implements  ILoginController{
    //public final static String type = null;
    public static  String EmployeeType;
    public static  int CurrentEmployee;
    //shouldn't be final
    private  EmployeeManager employeeManager;

    ILoginView loginView;
    public LoginController(ILoginView loginView) {

        this.loginView = loginView;
        employeeManager=new EmployeeManager((Context) loginView);
    }





    @Override
    public void onLogin(String username, String password) {
      int loginCode;

       // Construction d'un employé voulant se connecter
        Employee employee = new Employee(username,password);
         loginCode=employee.validUser();
        if(loginCode==EMPTY_USERNAME)
            loginView.onLoginError("Username requis !");
        else if(loginCode==INVALID_USERNAME)
            loginView.onLoginError("Username invalide !");
        else if(loginCode==EMPTY_PASSWORD)
            loginView.onLoginError("Mot de passe requis !");
        else if(loginCode==INVALID_PASSWORD)
            loginView.onLoginError("Mot de passe invalide !");
        else {
            employeeManager.open();
            ///ServiceManager s = new ServiceManager((Context) loginView);
            //s.open();
           /* (new ServiceManager((Context) loginView)).open();


            new PlanningManager((Context) loginView);
            new EmployeeManager((Context) loginView);
            new ClockingManager((Context) loginView);
            new DayManager((Context) loginView);*/
            //employeeManager.open();
            loginCode =employeeManager.connectUser(employee,password);
           employeeManager.close();



            if(loginCode==CAN_NOT_LOGIN)
                loginView.onLoginError("Username ou mot de passe incorrect !");
              else {
                loginView.onLoginSuccess("Authentification réussie");
                EmployeeType =employee.getType();
                CurrentEmployee=employee.getRegistrationNumber();
            }


        }

    }

    @Override

    public void onLoad(int savedVersionCode,int currentVersionCode) {
        final int DOESNT_EXIST=-1;

  if(savedVersionCode==currentVersionCode)
      loginView.onNormalRun();

  else if (savedVersionCode==DOESNT_EXIST)
        {
            /*ServiceManager serviceManager =new ServiceManager((Context) loginView);
           EmployeeManager e= new EmployeeManager((Context) loginView);
           e.open();
            serviceManager.open();

           /* new  ServiceManager((Context) loginView);

            new PlanningManager((Context) loginView);
            new DayManager((Context) loginView);

            new ClockingManager((Context) loginView);*/

            loginView.onFirstRun();
        }


  else if (savedVersionCode < currentVersionCode )
      loginView.onUpgrade();





    }

}

