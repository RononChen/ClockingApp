package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Controller.control.LoginController.EmployeeType;
import static uac.imsp.clockingapp.Models.entity.Employee.SIMPLE;

import java.util.Objects;

import uac.imsp.clockingapp.Controller.util.IMenuController;
import uac.imsp.clockingapp.View.util.IMenuView;

public class MenuController  implements IMenuController {
    private IMenuView menuView;

    public MenuController(IMenuView menuView){
        this.menuView=menuView;

    }


    @Override
    public void onSearchEmployeeMenu() {
   if(Objects.equals(EmployeeType, SIMPLE))
       menuView.onSearchEmployeeMenuError("Vous n'avez pas la permission de rechercher des employes");
   else

        menuView.onSearchEmployeeMenuSuccessfull();
    }

    @Override
    public void onUpdateEmployeeMenu() {
        if(Objects.equals(EmployeeType, SIMPLE))
            menuView.onUpdateEmployeeMenuError("Vous n'avez pas la permission de modifier des employés");
        else
            menuView.onUpdateEmployeeMenuSuccessfull();

    }

    @Override
    public void onDeleteEmployeeMenu() {
        if(Objects.equals(EmployeeType, SIMPLE))
            menuView.onDeleteEmployeeMenuError("Vous n'avez pas la permission de modifier des employés !");
        else
            menuView.onDeleteEmployeeMenuSucessfull();


    }

    @Override
    public void onRegisterEmployeeMenu() {
        if(Objects.equals(EmployeeType, SIMPLE))
            menuView.onRegisterEmployeeMenuError("Vous n'avez pas la permission de supprimer des employés !");
        else
            menuView.onRegisterEmployeeMenuSuccessful();

    }

    @Override
    public void onClocking() {
        menuView.onClocking();

    }

    @Override
    public void onConsultatisticsMenu() {
        if(Objects.equals(EmployeeType, SIMPLE))
            menuView.onConsultatisticsMenuError("Vous n'avez pas cette permission");
        else
            menuView.onConsultatisticsMenuSuccessful();

    }

    @Override
    public void onConsultPresenceReport() {
      //All employees can cansult threir presence report
    }
}

