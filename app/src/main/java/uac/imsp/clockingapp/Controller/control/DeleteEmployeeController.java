package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.util.IDeleteEmployeeController;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.dao.PlanningManager;
import uac.imsp.clockingapp.Models.dao.ServiceManager;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.Models.entity.Planning;
import uac.imsp.clockingapp.Models.entity.Service;
import uac.imsp.clockingapp.View.util.IDeleteEmployeeView;

public class DeleteEmployeeController implements IDeleteEmployeeController {
    IDeleteEmployeeView deleteEmployeeView;
    private Employee employee;
    private EmployeeManager employeeManager;
    private Context context;

    public DeleteEmployeeController(IDeleteEmployeeView deleteEmployeeView)


    {
        this.deleteEmployeeView=deleteEmployeeView;
        this.context=(Context) this.deleteEmployeeView;


    }
    public DeleteEmployeeController(IDeleteEmployeeView deleteEmployeeView,
                                    Context context){
        this.deleteEmployeeView=deleteEmployeeView;
        this.context=context;

    }
    @Override
    //get informations on load

    public  void onLoad(int number, Hashtable <String,Object> informations) {


        Day day;

        Service service;

        Planning planning;
        PlanningManager planningManager = new PlanningManager(context);
        ServiceManager serviceManager = new ServiceManager(context);
        employeeManager=new EmployeeManager(context);
        employeeManager.open();
        planningManager.open();
        serviceManager.open();
        employee=new Employee(number);
        employeeManager.setInformations(employee);
        planning=employeeManager.getPlanning(employee);
        service =employeeManager.getService(employee);

        informations.put("number",String.valueOf(employee.getRegistrationNumber()));
        informations.put("lastname",employee.getLastname());
        informations.put("firstname",employee.getFirstname());

        try {

            informations.put("picture", getBitMapFromBytes(employee.getPicture()));
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        informations.put("email",employee.getMailAddress());
        informations.put("username",employee.getUsername());
        informations.put("gender",employee.getGender());
        day=new Day(employee.getBirthdate());
        informations.put("birthdate",day.getFrenchFormat());
        informations.put("type",employee.getType());
        informations.put("service",service.getName());
        informations.put("start",Integer.parseInt(Objects.requireNonNull(planning.extractHours().get("start"))));
        informations.put("end",Integer.parseInt(Objects.requireNonNull(planning.extractHours().get("end"))));

    }



    @Override
    public void onDeleteEmployee() {

        deleteEmployeeView.askConfirmDelete("Oui","Non", "Confirmation",
                "Voulez vous vraiment supprimer l'employé ?");




    }

    @Override
    public void onConfirmResult(Boolean confirmed) {


        if(!confirmed)
            return;
        employeeManager=new EmployeeManager(context);
        employeeManager.open();

        employeeManager.setInformations(employee);


        employeeManager.close();

            deleteEmployeeView.onDeleteSuccessfull("Employé supprimé avec succès");

    }
    public static Bitmap getBitMapFromBytes(byte[] array){

        return BitmapFactory.decodeByteArray(array,0,array.length);



    }
}
