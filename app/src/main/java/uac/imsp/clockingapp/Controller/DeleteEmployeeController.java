package uac.imsp.clockingapp.Controller;

import static uac.imsp.clockingapp.Controller.UpdateEmployeeController.getBitMapFromBytes;

import android.content.Context;

import java.text.ParseException;
import java.util.Hashtable;

import uac.imsp.clockingapp.Models.Day;
import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.Models.Planning;
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

    @Override
    public  String [] onLoad(int number, Hashtable<String,Object> informations) throws ParseException {
        Day day;
        ServiceManager serviceManager;
        Service service;
        Planning planning;
        employee=new Employee(number);
        employeeManager=new EmployeeManager((Context) deleteEmployeeView);
                employeeManager.open();
        employeeManager.setInformations(employee);
        planning=employeeManager.getPlanning(employee);
        service =employeeManager.getService(employee);

        informations.put("number",String.valueOf(employee.getRegistrationNumber()));
        informations.put("lastname",employee.getLastname());
        informations.put("firstname",employee.getFirstname());
        informations.put("picture",   getBitMapFromBytes(employee.getPicture()));
        informations.put("email",employee.getMailAddress());
        informations.put("username",employee.getUsername());
        informations.put("gender",employee.getGender());
        day=new Day(employee.getBirthdate());
        informations.put("birthdate",day.getFrenchFormat());
        informations.put("type",employee.getType());
        informations.put("service",service.getName());
        informations.put("start",Integer.parseInt(planning.getStartTime()));
        informations.put("end",Integer.parseInt(planning.getEndTime()));



        serviceManager = new ServiceManager((Context) deleteEmployeeView);


        serviceManager.open();
        String[] serviceList =serviceManager.getAllServices();
        serviceManager.close();
        return serviceList;
    }

    @Override
    public void onDeleteEmployee() {
        //handle clic on delete buttton

        deleteEmployeeView.askConfirmDelete("Oui","Non","Confirmation","Voulez vous " +
                "vraiment supprimer l'employé ?");

        employeeManager=new EmployeeManager((Context) deleteEmployeeView);
        employeeManager.open();

            employeeManager.delete(employee);

        employeeManager.close();
            deleteEmployeeView.onDeleteSucessfull("Employé supprimé avec succès");


    }



}
