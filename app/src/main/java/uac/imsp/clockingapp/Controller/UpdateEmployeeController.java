package uac.imsp.clockingapp.Controller;

import android.content.Context;

import java.util.Objects;

import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.Models.Planning;
import uac.imsp.clockingapp.Models.Service;
import uac.imsp.clockingapp.Models.ServiceManager;
import uac.imsp.clockingapp.View.IUpdateEmployeeView;

public class UpdateEmployeeController implements IUpdateEmployeeController {
    IUpdateEmployeeView  updateEmployeeView;
    public UpdateEmployeeController(IUpdateEmployeeView updateEmployeeView)
    {
        this.updateEmployeeView=updateEmployeeView;
    }
    private Employee employee;
    private int Number;
    private Service service;
    private Planning planning;

    @SuppressWarnings("UnusedAssignment")
    @Override
    //Pour obtenir les listes de matricules et de services au chargement

    public  String [] onLoad(String [] serviceList) {

     String [] employeeList = forWhom(Number);
        ServiceManager serviceManager;
        serviceManager =new ServiceManager((Context) updateEmployeeView);
        serviceManager.open();
        serviceList=serviceManager.getAllServices();
        serviceManager.close();
        return employeeList;

    }

    @Override
    public void onNumberSelected(int number) {
                //Si on le matricule est changé, on change l'employé
        Number=number;
     employee=new Employee(Number);
    }

    @Override
    public void onUpdateEmployee(String selectedService, String startTime, String endTime, byte[] picture) {
     //gestion du clic sur modifier employé
        boolean update=false;
        EmployeeManager employeeManager=new EmployeeManager((Context) updateEmployeeView);
        employeeManager.open();
     if(!Objects.equals(service.getName(), selectedService)) {
         employeeManager.update(employee, service);
         update = true;
     }
     if(!Objects.equals(planning.getStartTime(), startTime) || !Objects.equals(planning.getEndTime(), endTime)) {
         employeeManager.update(employee, planning);
         update=true;
     }
     if(employee.getPicture()!=picture) {
         employeeManager.update(employee, picture);
         update=true;
     }
     employeeManager.close();

     if(update)
         updateEmployeeView.onSomethingchanged("Employé modifié avec succès");
     else
         updateEmployeeView.onNothingChanged("Aucune information n'a été modifiée");

    }
    //@SuppressWarnings("UnusedAssignment")
    public String [] forWhom(int number){
        String [] employeeList;
        EmployeeManager employeeManager;
        employee = new Employee(number);
        employeeManager=new EmployeeManager((Context) updateEmployeeView);
        employeeManager.open();
        employeeList=employeeManager.getAllEmployees();
        employeeManager.setInformations(employee);
        planning=employeeManager.getPlanning(employee);
        service=employeeManager.getService(employee);
        employeeManager.close();
        return employeeList;
    }
}
