package uac.imsp.clockingapp.Controller;

import static uac.imsp.clockingapp.Controller.RegisterEmployeeController.getBytesFromBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Models.Day;
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
    private EmployeeManager employeeManager;




    @Override
    //Pour obtenir les listes de matricules et de services au chargement

    public  String [] onLoad(int number, Hashtable <String,Object> informations) throws ParseException {
        Day day;
  ServiceManager serviceManager;
  Service service;
  Planning planning;
       employee=new Employee(number);
       employeeManager=new EmployeeManager((Context) updateEmployeeView);
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



         serviceManager = new ServiceManager((Context) updateEmployeeView);


        serviceManager.open();
        String[] serviceList =serviceManager.getAllServices();
        serviceManager.close();
        return serviceList;
    }



    @Override
    public void onUpdateEmployee(String mail, String selectedService, int startTime,
                                 int endTime, Bitmap picture, String type) {
     //gestion du clic sur modifier employé
        //Day day;
        Service service;
        Planning planning;
        String s,e;

        updateEmployeeView.askConfirmUpdate("Oui","Non","Confirmation","Voulez vous " +
                "vraiment appliquer ces modifications ?");

        s=formatTime(startTime);
        e=formatTime(endTime);
        service=new Service(selectedService);
        planning=new Planning(s,e);
        boolean update=false;
         employeeManager=new EmployeeManager((Context) updateEmployeeView);
        employeeManager.open();
        employeeManager.setInformations(employee);

        if(!Objects.equals(employee.getMailAddress(), mail))

        {
            if(TextUtils.isEmpty(mail))
            updateEmployeeView.onUpdateEmployeeError("L'adresse email est requis !");
            else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                updateEmployeeView.onUpdateEmployeeError("Adresse email invalide !");
            else {
                employeeManager.update(employee, mail);
                update = true;
            }
        }

        if(!Objects.equals(employee.getType(), type))
        {
            employeeManager.changeGrade(employee,type);
            update=true;
        }
     if(!Objects.equals(service.getName(), selectedService)) {
         employeeManager.update(employee, service);
         update = true;
     }
     if(!Objects.equals(planning.getStartTime(), String.valueOf(startTime)) ||
             !Objects.equals(planning.getEndTime(), String.valueOf(endTime))) {
         employeeManager.update(employee, planning);
         update=true;
     }

     if(employee.getPicture()!=getBytesFromBitmap(picture)) {
         employeeManager.update(employee, getBytesFromBitmap(picture));

         update=true;
     }
     employeeManager.close();

     if(update)
         updateEmployeeView.onSomethingchanged("Employé modifié avec succès");
     else
         updateEmployeeView.onNothingChanged("Aucune information n'a été modifiée");

    }

    @Override
    public void onReset() {
        updateEmployeeView.askConfirmReset("Oui","Non","Confirmation","Voulez vous vraiment annuler ces modifications ?");

    }


    public String formatTime(int time) {
        if(time <10)
            return "0"+time+":"+"0";
        else
            return time+":"+"0";
    }
public static Bitmap getBitMapFromBytes(byte[] array){
        return BitmapFactory.decodeByteArray(array,0,array.length);


}

}
