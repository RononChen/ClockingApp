package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.text.TextUtils;

import uac.imsp.clockingapp.Controller.util.IChangePasswordController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IChangePasswordView;

public class ChangePasswordController implements IChangePasswordController {

private IChangePasswordView changePasswordView;
private Employee employee;
  private   EmployeeManager employeeManager;
  private Context context;
  private String oldPasssword;
    public ChangePasswordController(IChangePasswordView changePasswordView){
    this.changePasswordView=changePasswordView;

    this.context= (Context) this.changePasswordView;
    }
    public ChangePasswordController(IChangePasswordView changePasswordView,Context context){
        this.changePasswordView=changePasswordView;
        this.context=context;
    }



   public void onStart(int number) {
       employee = new Employee(number);
          employeeManager = new EmployeeManager(context);
          employeeManager.open();
           employeeManager.setAccount(employee);
           //The view gets the username from the controller
           changePasswordView.onStart(employee.getUsername());
          // oldPassword=

       }


    public String getOldPasssword() {
        return oldPasssword;
    }

    public void setOldPasssword(String oldPasssword) {
        this.oldPasssword = oldPasssword;
    }

    @Override
    public void onSubmit(String oldPasswordEdited, String newPassword,String newPasswordConfirm) {
        try {
             if(TextUtils.isEmpty(oldPasswordEdited) ||
                     TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(newPasswordConfirm)
             )
                 changePasswordView.onWrongPassword("Tous les champs sont requis !");

           else if (!oldPasswordEdited.matches("\\w{6,}") ||
                     !newPassword.matches("\\w{6,}"))


                changePasswordView.onWrongPassword("Mot de passe invalide !");
            else if (!oldPasswordEdited.equals(employee.getPassword()))
                changePasswordView.onWrongPassword("Mot de passe incorrect !");

            else if(!newPasswordConfirm.equals(newPassword))
                changePasswordView.onWrongPassword("Vérifiez le mot de passe précédent et reessayez !");
            else {
                employeeManager.changePassword(employee, newPassword);
                changePasswordView.onSuccess("Mot de passe changé avec succès");
            }
        }

        catch(NullPointerException e ){
            changePasswordView.onWrongPassword("Tous les champs sont requis !");
            }




    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }
    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}