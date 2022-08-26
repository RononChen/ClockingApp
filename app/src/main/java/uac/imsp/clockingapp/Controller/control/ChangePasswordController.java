package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.IChangePasswordController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IChangePasswordView;

public class ChangePasswordController implements IChangePasswordController {

private IChangePasswordView changePasswordView;
private Employee employee;
    EmployeeManager employeeManager;
    public ChangePasswordController(IChangePasswordView changePasswordView){

this.changePasswordView=changePasswordView;
    }
    void onStart(int number){
        employee=new Employee(number);

        employeeManager=new EmployeeManager((Context) changePasswordView);
        employeeManager.setAccount(employee);
        //The view gets the username from the controller
        changePasswordView.onStart(employee.getUsername());


        
        }

    @Override
    public void onSubmit(String oldPassword, String newPassword) {
        if(!newPassword.matches("\\w{6,}"))
            changePasswordView.onWrongPassword("Mot de passe invalide");
      else  if(!oldPassword.equals(employee.getPassword()))
            changePasswordView.onWrongPassword("Mot de passe incorrect");
        else {
            employeeManager.changePassword(employee,newPassword);
            changePasswordView.onSuccess("Mot de passe changé avec succès");


        }

    }
}