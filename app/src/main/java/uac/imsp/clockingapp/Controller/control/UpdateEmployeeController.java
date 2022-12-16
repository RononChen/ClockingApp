package uac.imsp.clockingapp.Controller.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Objects;

import dao.EmployeeManager;
import dao.PlanningManager;
import dao.ServiceManager;
import entity.Day;
import entity.Employee;
import entity.Planning;
import entity.Service;
import uac.imsp.clockingapp.Controller.util.IUpdateEmployeeController;
import uac.imsp.clockingapp.View.util.IUpdateEmployeeView;

public class UpdateEmployeeController implements IUpdateEmployeeController {

    IUpdateEmployeeView  updateEmployeeView;
    private Employee employee;
    private EmployeeManager employeeManager;
    private ServiceManager serviceManager;
   private final Context context;
    Service service;
    Planning planning;
    boolean serviceUpdated,typeUpdated,pictureUpdated,planningUpdated,mailUpdated;


    public UpdateEmployeeController(IUpdateEmployeeView updateEmployeeView)
    {
        this.updateEmployeeView=updateEmployeeView;
        this.context= (Context) updateEmployeeView;

    }
  public UpdateEmployeeController(IUpdateEmployeeView updateEmployeeView,
                                  Context context){
        this.updateEmployeeView=updateEmployeeView;
        this.context=context;

  }







    @Override
    //get service list and employee informations on load


    public  String [] onLoad(int number, Hashtable <String,Object> informations) {
        Day day;

  Service service;
  String birthdate;
  Planning planning;
  String [] serviceList;
        //planningManager=new PlanningManager(context);

        employeeManager=new EmployeeManager(context);

        //planningManager.open();

        employee=new Employee(number);
        employeeManager.open();
       employeeManager.setInformations(employee);
       planning=employeeManager.getPlanning(employee);
       service =employeeManager.getService(employee);
       employeeManager.close();

        serviceManager=new ServiceManager(context);
       serviceManager.open();
        serviceList =serviceManager.getAllServices();
       serviceManager.searchService(service);//set id
        serviceManager.close();


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

            e.printStackTrace();

        }
        informations.put("birthdate",birthdate);

        informations.put("type",employee.getType());
        informations.put("service",service.getName());
        informations.put("start",Integer.parseInt(Objects.requireNonNull(planning.extractHours().get("start"))));
        informations.put("end",Integer.parseInt(Objects.requireNonNull(planning.extractHours().get("end"))));
        informations.put("workDays",planning.getWorkDays());
        return serviceList;

    }
    @Override
    public void onUpdateEmployee(String mail, String selectedService,
                                 int startTime,
                                 int endTime,byte[] workDays, Bitmap picture, String type) {


        if (mailUpdated) {
            if (TextUtils.isEmpty(mail))
                updateEmployeeView.onUpdateEmployeeError
                        (0);

            else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                updateEmployeeView.onUpdateEmployeeError(1);
            else {

                updateEmployeeView.onSomethingchanged();
                employeeManager=new EmployeeManager(context);
                employeeManager.open();
                employeeManager.update(employee, mail);
            }

        }
        else
            updateEmployeeView.onSomethingchanged();



            String s,e;
            PlanningManager planningManager = new PlanningManager(context);
            serviceManager=new ServiceManager(context) ;

            planningManager.open();
            serviceManager.open();


            service=new Service(selectedService);
            serviceManager.create(service);

            s=formatTime(startTime);

            e=formatTime(endTime);
            //workDays=
            planning=new Planning(s,e,workDays);

            planningManager.create(planning);//To set the planning Id


            employeeManager=new EmployeeManager(context);
            employeeManager.open();
            employeeManager.setInformations(employee);

            employeeManager.update(employee, mail);


            if (typeUpdated)
                employeeManager.changeGrade(employee, type);


            if (serviceUpdated)

                employeeManager.update(employee, service);
            if(planningUpdated)

                employeeManager.update(employee,planning);

            if (pictureUpdated)
                employeeManager.update(employee, getBytesFromBitmap(picture));
            employeeManager.close();

    }

    @NonNull
    private byte[] getBytesFromBitmap(@NonNull Bitmap picture) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 70, stream);
            return stream.toByteArray();
    }

    @Override
    public void onReset() {

    }

    @Override
    public void onUpdateEmployee(boolean emailUpdated, boolean serviceUpdated,
                                 boolean planningUpdated, boolean pictureUpdated,
                                 boolean typeUpdated) {

        if(!emailUpdated&&!serviceUpdated&&!pictureUpdated&&
                !planningUpdated&&!typeUpdated)
            updateEmployeeView.onNothingChanged();
        else {

            this.serviceUpdated=serviceUpdated;
            this.typeUpdated=typeUpdated;
           this.pictureUpdated=pictureUpdated;
          this.planningUpdated=planningUpdated;
         this.mailUpdated=emailUpdated;
         updateEmployeeView.askConfirmUpdate();
        }




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

}
