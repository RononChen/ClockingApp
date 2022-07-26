package uac.imsp.clockingapp.Controller;

import android.content.Context;

import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.Models.Service;
import uac.imsp.clockingapp.Models.ServiceManager;
import uac.imsp.clockingapp.View.IDeleteEmployeeView;

public class DeleteEmployeeController  implements IDeleteEmployeeController{

 IDeleteEmployeeView deleteEmployeeView;
 public DeleteEmployeeController(IDeleteEmployeeView deleteEmployeeView){
     this.deleteEmployeeView=deleteEmployeeView;
 }
private Employee employee;
 private EmployeeManager employeeManager;
 private int Number;
 private Service service;



    @SuppressWarnings("UnusedAssignment")
    @Override
    public String[] onLoad(String[] serviceList) {
        ServiceManager serviceManager;
        String [] employeeList;
        serviceManager =new ServiceManager((Context) deleteEmployeeView);
        serviceManager.open();
        serviceList=serviceManager.getAllServices();
       serviceManager.close();

        employee = new Employee(Number);
        employeeManager=new EmployeeManager((Context) deleteEmployeeView);
        employeeManager.open();
        employeeList=employeeManager.getAllEmployees();
        employeeManager.setInformations(employee);
        service=employeeManager.getService(employee);
        employeeManager.close();
        return employeeList;

    }

    @Override
    public void onDelete(int number) {
      Number=number;
        deleteEmployeeView.askConfirm("Oui","Non","Confirmation de la suppression","Voulez vous vraiment supprimer cet employé ?");


    }

    @Override
    public void onDeleteConfirmed() {
        employeeManager=new EmployeeManager((Context) deleteEmployeeView);
        employeeManager.open();
        employeeManager.delete(employee);
        employeeManager.close();
        deleteEmployeeView.onDeleteSucessful("Suppression effectuée avec succès");

    }




    @Override
    public void onNumberSelected(int number) {
        //Si le matricule est changé, on  change l'employé et on recharge la fenetre
        Number =number;
        employee = new Employee(number);
        onLoad(null);
    }


}
