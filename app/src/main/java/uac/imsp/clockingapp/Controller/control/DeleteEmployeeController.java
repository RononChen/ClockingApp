package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Hashtable;
import java.util.Objects;

import dao.EmployeeManager;
import dao.PlanningManager;
import dao.ServiceManager;
import entity.Day;
import entity.Employee;
import entity.Planning;
import entity.Service;
import uac.imsp.clockingapp.Controller.util.IDeleteEmployeeController;
import uac.imsp.clockingapp.View.util.IDeleteEmployeeView;

public class DeleteEmployeeController implements IDeleteEmployeeController {
    IDeleteEmployeeView deleteEmployeeView;
    private Employee employee;
    private EmployeeManager employeeManager;
    private final Context context;

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
String birthdate;
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
        try {
            day = new Day(employee.getBirthdate());
            birthdate=day.getFrenchFormat();

        }
        catch (NullPointerException e){
            birthdate="";
            informations.put("birthdate",birthdate);
            e.printStackTrace();

        }
        informations.put("birthdate",birthdate);
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
        //employeeManager.setInformations(employee);
        employeeManager.delete(employee);



        //employeeManager.close();

            deleteEmployeeView.onDeleteSuccessfull();

    }
    public static Bitmap getBitMapFromBytes(byte[] array){

        return BitmapFactory.decodeByteArray(array,0,array.length);

    }
}
