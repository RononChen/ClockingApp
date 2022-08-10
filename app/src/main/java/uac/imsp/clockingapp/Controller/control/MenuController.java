package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.IMenuController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IMenuView;

public class MenuController  implements IMenuController {
    private IMenuView menuView;
    private Employee employee;
    private EmployeeManager employeeManager;


    public MenuController(IMenuView menuView){
        this.menuView=menuView;


    }


    @Override
    public void onSearchEmployeeMenu(int currentUser) {
        employeeManager=new EmployeeManager((Context) menuView);
        employeeManager.open();
        employee=new Employee(currentUser);

   if(employeeManager.isNotSuperUser(employee))
       menuView.onSearchEmployeeMenuError("Vous n'avez pas la permission de rechercher des employes");
   else
        menuView.onSearchEmployeeMenuSuccessfull();
        employeeManager.close();
    }

    @Override
    public void onUpdateEmployeeMenu(int currentUser) {
        employeeManager=new EmployeeManager((Context) menuView);
        employeeManager.open();
        employee=new Employee(currentUser);

        if(employeeManager.isNotSuperUser(employee))
            menuView.onUpdateEmployeeMenuError("Vous n'avez pas la permission de modifier des employés");
        else
            menuView.onUpdateEmployeeMenuSuccessfull();
        employeeManager.close();

    }

    @Override
    public void onDeleteEmployeeMenu(int currentUser) {
        employeeManager=new EmployeeManager((Context) menuView);
        employeeManager.open();
        employee=new Employee(currentUser);

        if(employeeManager.isNotSuperUser(employee))
            menuView.onDeleteEmployeeMenuError("Vous n'avez pas la permission de modifier des employés !");
        else
            menuView.onDeleteEmployeeMenuSucessfull();

        employeeManager.close();


    }


    @Override
    public void onRegisterEmployeeMenu(int currentUser) {

        employeeManager=new EmployeeManager((Context) menuView);
        employeeManager.open();
        employee=new Employee(currentUser);

        if(employeeManager.isNotSuperUser(employee))

            menuView.onRegisterEmployeeMenuError("Vous n'avez pas la permission de supprimer des employés !");
        else
            menuView.onRegisterEmployeeMenuSuccessful();
      employeeManager.close();
    }

    @Override
    public void onClocking() {
        menuView.onClocking();

    }

    @Override
    public void onConsultatisticsMenu(int currentUser) {
        employeeManager=new EmployeeManager((Context) menuView);
        employeeManager.open();
        employee=new Employee(currentUser);

        if(employeeManager.isNotSuperUser(employee))
            menuView.onConsultatisticsMenuError("Vous n'avez pas cette permission");
        else
            menuView.onConsultatisticsMenuSuccessful();
        employeeManager.close();

    }


    @Override
    public void onConsultPresenceReport() {

      //All employees can cansult threir presence report
    }
}

