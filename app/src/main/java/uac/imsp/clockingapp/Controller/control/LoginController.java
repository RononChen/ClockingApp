package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import dao.EmployeeManager;
import entity.Day;
import entity.Employee;
import uac.imsp.clockingapp.Controller.util.ILoginController;
import uac.imsp.clockingapp.View.util.ILoginView;

public class LoginController  implements ILoginController {
    //public final static String type = null;
    public static  int CurrentEmployee;
    private  int attempNumber=0;
    private Context context;
    //shouldn't be final
    private EmployeeManager employeeManager;
    Runnable runnable;

    ILoginView loginView;
    public LoginController(ILoginView loginView) {

        this.loginView = loginView;
        this.context=(Context) this.loginView;

    }





    @Override
    public void onLogin(String username, String password) {
      int loginCode;
        employeeManager=new EmployeeManager((Context) loginView);
        employeeManager.open();

       // Construction d'un employé voulant se connecter
        Employee employee = new Employee(username,password);
        employee=new Employee("User10","Aab10%");
        //employee.setType("Simple");

         /*loginCode=employee.validUser();

        if(loginCode==EMPTY_USERNAME)
            loginView.onLoginError("Username requis !");
        else if(loginCode==INVALID_USERNAME)
            loginView.onLoginError("Username invalide !");
        else if(loginCode==EMPTY_PASSWORD)
            loginView.onLoginError("Mot de passe requis !");
        else if(loginCode==INVALID_PASSWORD)
            loginView.onLoginError("Mot de passe invalide !");
        else {*/

            employeeManager=new EmployeeManager((Context) loginView);
            employeeManager.open();

            loginCode =employeeManager.connectUser(employee);

            employeeManager.close();


           /* if(loginCode==CAN_NOT_LOGIN) {
                loginView.onLoginError("Username ou mot de passe incorrect !");
                attempNumber++;
                if(attempNumber==3)
                    loginView.onMaxAttempsReached("Trois tentatives d'authentification échouées !");



            }
              else {*/
                CurrentEmployee=employee.getRegistrationNumber();
        loginView.onLoginSuccess(CurrentEmployee);

        if(employee.isAdmin())
                       loginView.askWish(
                       );
        else
            loginView.onSimpleEmployeeLogin();

            //}




        //}
        loginView.askWish();


    }
    @Override
    public void onClocking() {
        loginView.onClocking();

    }

    @Override
    public void onUsernameEdit(@NonNull String username) {

        String uname=username.trim();
        if(TextUtils.isEmpty(uname))
            loginView.onUsernameError("Requis !");
        else if(uname.length()<6)
            loginView.onUsernameError("Au moins 6 caractères requis !");
        else if(uname.length()>30)
            loginView.onUsernameError("Au plus 30 caractères requis !");
    }

    @Override
    public void onPasswordEdit(@NonNull String password) {
        String pwd=password.trim();

        if(TextUtils.isEmpty(pwd))
            loginView.onPasswordError("Requis !");
        else if(pwd.length()<6)
            loginView.onPasswordError("Au moins 6 caractères requis !");


    }

    @Override
    public void onShowHidePassword() {


        loginView.onShowHidePassword();

    }
    public void updateAttendance(Employee employee){
        runnable= () -> {
            String statut;
            employeeManager=new EmployeeManager((Context) loginView);
            employeeManager.open();
            if(employeeManager.shouldNotWorkToday(employee))
            {
                if(new Day().isWeekEnd())
                    statut="Week-end";


                else
                    statut="Hors service";

            }
            else
                statut="Absent";
            employeeManager.updateCurrentAttendance(employee,statut);
            employeeManager.close();


        };
        AsyncTask.execute(runnable);



    }

    @Override
    public void onConfirmResult(boolean confirmed) {
        if (confirmed) {
            runnable= () -> {
                employeeManager=new EmployeeManager((Context) loginView);
                employeeManager.open();
                Employee[] employees =employeeManager.search("*");
                employeeManager.close();
                for(Employee employee:employees)
                    updateAttendance(employee);

            };
            AsyncTask.execute(runnable);
            loginView.onPositiveResponse();
        }
        else

            loginView.onNegativeResponse();

    }



}

