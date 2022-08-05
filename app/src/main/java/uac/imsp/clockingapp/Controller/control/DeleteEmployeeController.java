package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Controller.control.RegisterEmployeeController.getBytesFromBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
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
    private  PlanningManager planningManager;
    private  ServiceManager serviceManager;
    Service service;
    Planning planning;
    String mail,type;

    Bitmap picture;
    public DeleteEmployeeController(IDeleteEmployeeView deleteEmployeeView)

    {
        this.deleteEmployeeView=deleteEmployeeView;

    }





    @Override
    //Pour obtenir les listes de matricules et de services au chargement

    public  String [] onLoad(int number, Hashtable <String,Object> informations) throws ParseException {
        Day day;

        Service service;
        Planning planning;
        String [] serviceList;
        planningManager=new PlanningManager((Context) deleteEmployeeView);
        serviceManager=new ServiceManager((Context) deleteEmployeeView);
        employeeManager=new EmployeeManager((Context) deleteEmployeeView);
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

        // if(employee.getPicture()!=null)
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

        serviceList =serviceManager.getAllServices();
        return serviceList;

    }



    @Override
    public void onDeleteEmployee(String mail, String selectedService, int startTime,
                                 int endTime, Bitmap picture, String type) {
        String s,e;
        //gestion du clic sur modifier employé

        planningManager=new PlanningManager((Context) deleteEmployeeView);
        serviceManager=new ServiceManager((Context) deleteEmployeeView);
        employeeManager=new EmployeeManager((Context) deleteEmployeeView);
        employeeManager.open();
        planningManager.open();
        serviceManager.open();

        this.mail=mail;
        service=new Service(selectedService);
        /*serviceManager=new ServiceManager((Context) updateEmployeeView);
        serviceManager.open();*/
        serviceManager.create(service);

        s=formatTime(startTime);

        e=formatTime(endTime);
        planning=new Planning(s,e);
        planningManager.create(planning);//To set the planning Id

        this.picture=picture;
        this.type=type;


        employeeManager.setInformations(employee);

        deleteEmployeeView.askConfirmUpdate("Oui","Non",

                "Confirmation","Voulez vous " +
                        "vraiment appliquer ces modifications ?");



    }

    @Override
    public void onConfirmResult(Boolean confirmed) {

        boolean update=false;

        // assert confirmed;
        if(!confirmed)
            return;


        //compare the email entred to the real email of the emp
        if (!Objects.equals(employee.getMailAddress(), mail)) {
            if (TextUtils.isEmpty(mail))
                deleteEmployeeView.onDeleteEmployeeError("L'adresse email est requis !");

            else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                deleteEmployeeView.onDeleteEmployeeError("Adresse email invalide !");
            else {
                employeeManager.update(employee, mail);
                update = true;
            }
        }

        if (!Objects.equals(employee.getType(), type) &&
                type != null) {
            employeeManager.changeGrade(employee, type);
            update = true;
        }
        if (!Objects.equals(employeeManager.getService(employee), service)
        ) {
            employeeManager.update(employee, service);
            update = true;
        }
        if (!Objects.equals(employeeManager.getPlanning(employee), planning)) {
            employeeManager.update(employee, planning);
            update = true;
        }

        if (employee.getPicture() != getBytesFromBitmap(picture)) {
            employeeManager.update(employee, getBytesFromBitmap(picture));

            update = true;
        }
        employeeManager.close();

        if (update)
            deleteEmployeeView.onSomethingchanged("Employé supprimé avec succès");
        else
            deleteEmployeeView.onNothingChanged("Aucune information n'a été modifiée");




    }



    public String formatTime(int time) {
        if(time <10)
            return "0"+time+":"+"00";

        else
            return time+":"+"00";
    }
    public static Bitmap getBitMapFromBytes(byte[] array){

        return BitmapFactory.decodeByteArray(array,0,array.length);



    }
//public  String extractHour()

}
