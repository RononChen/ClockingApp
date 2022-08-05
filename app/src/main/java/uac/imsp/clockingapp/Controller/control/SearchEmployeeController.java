package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import uac.imsp.clockingapp.Controller.util.ISearchEmployeeController;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.View.util.IsearchEmployeeView;

public class SearchEmployeeController  implements ISearchEmployeeController {
    private IsearchEmployeeView searchEmployeeView ;
    public SearchEmployeeController(IsearchEmployeeView searchEmployeeView){
        this.searchEmployeeView=searchEmployeeView;
    }

    @Override
    public List<Result> onSearch(String data) {
        int number;
        String lastname,firstname,service;
        List <Result>  list= new ArrayList<>();
        Result result;


        Employee[] employeeSet;
        EmployeeManager employeeManager;

        employeeManager=new EmployeeManager((Context) searchEmployeeView);
                employeeManager.open();
        employeeSet =employeeManager.search(data);

        if(employeeSet.length==0)
            searchEmployeeView.onNoEmployeeFound("Aucun employé correspondant");
        else
            for (Employee employee : employeeSet)
            {
              number=employee.getRegistrationNumber();
              lastname=employee.getLastname();
              firstname=employee.getFirstname();
             service=employeeManager.getService(employee).getName();
             result=new Result(number,lastname,firstname,service);
             list.add(result);

        }

        employeeManager.close();
        return null;
    }
}

