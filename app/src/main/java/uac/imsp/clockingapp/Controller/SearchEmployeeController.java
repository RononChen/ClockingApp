package uac.imsp.clockingapp.Controller;

import android.content.Context;

import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.View.IsearchEmployeeView;

public class SearchEmployeeController  implements ISearchEmployeeController{
    private IsearchEmployeeView searchEmployeeView ;
    public SearchEmployeeController(IsearchEmployeeView searchEmployeeView){
        this.searchEmployeeView=searchEmployeeView;
    }

    @Override
    public void onSearch(String data) {
        int number;
        String lastname,firstname,service;
        byte[] picture;

        Employee[] employeeSet;
        EmployeeManager employeeManager;

        employeeManager=new EmployeeManager((Context) searchEmployeeView);
                employeeManager.open();
        employeeSet =employeeManager.search(data);

        if(employeeSet.length==0)
            searchEmployeeView.onNoEmployeeFound("Aucun employé correspondant");
        else
        {
            for (Employee employee : employeeSet)
            {
              number=employee.getRegistrationNumber();
              lastname=employee.getLastname();
              firstname=employee.getFirstname();
              picture=employee.getPicture();
             service=employeeManager.getService(employee).getName();
             searchEmployeeView.onEmployeeFound(number,lastname,firstname,picture,service);

            }

        }

        employeeManager.close();
    }
}

