package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Models.dao.EmployeeManager.CAN_NOT_LOGIN;
import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_PASSWORD;
import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_USERNAME;
import static uac.imsp.clockingapp.Models.entity.Employee.INVALID_PASSWORD;
import static uac.imsp.clockingapp.Models.entity.Employee.INVALID_USERNAME;

import android.content.Context;
import android.text.TextUtils;

import uac.imsp.clockingapp.Controller.util.ILoginController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.ILoginView;

public class LoginController  implements ILoginController {
    //public final static String type = null;
    public static  int CurrentEmployee;
    private  int attempNumber=0;
    //shouldn't be final
    private  EmployeeManager employeeManager;

    ILoginView loginView;
    public LoginController(ILoginView loginView) {

        this.loginView = loginView;

    }





    @Override
    public void onLogin(String username, String password) {
      int loginCode;
        employeeManager=new EmployeeManager((Context) loginView);
        employeeManager.open();

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

            //employeeManager=new EmployeeManager((Context) loginView);
            //employeeManager.open();

            loginCode =employeeManager.connectUser(employee,password);

          //  employeeManager.close();


            if(loginCode==CAN_NOT_LOGIN) {
                loginView.onLoginError("Username ou mot de passe incorrect !");
                attempNumber++;
                if(attempNumber==3)
                    loginView.onMaxAttempsReached("Trois tentatives d'authentification échouées !");



            }
              else {
                CurrentEmployee=employee.getRegistrationNumber();
                  if(!employee.getType().equals("Simple"))
                       loginView.askWish("Oui","Non","Choix du type de connexion",
                               "Voulez vous vous connecter en tant qu'administrateur?");
                loginView.onLoginSuccess("Authentification réussie",
                        CurrentEmployee
                );
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


            loginView.onFirstRun();
        }


  else if (savedVersionCode < currentVersionCode )
      loginView.onUpgrade();





    }

    @Override
    public void onClocking() {
        loginView.onClocking();

    }

    @Override
    public void onUsernameEdit(String username) {

        String uname=username.trim();
        if(TextUtils.isEmpty(uname))
            loginView.onUsernameError("Requis !");
        else if(uname.length()<6)
            loginView.onUsernameError("Au moins 6 caractères requis !");
        else if(uname.length()>30)
            loginView.onUsernameError("Au plus 30 caractères requis !");
    }

    @Override
    public void onPasswordEdit(String password) {
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

    @Override
    public void onConfirmResult(boolean confirmed) {
        if (confirmed)
            loginView.onPositiveResponse("Vous êtes connecté en tant qu'administrateur !");
        else
            loginView.onNegetiveResponse("Vous êtes connecté en tant que" +
                    "simple employé !");

    }



}

