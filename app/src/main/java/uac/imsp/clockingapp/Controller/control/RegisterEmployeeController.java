package uac.imsp.clockingapp.Controller.control;

import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_BIRTHDATE;
import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_FIRSTNAME;
import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_LASTNAME;
import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_MAIL;
import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_NUMBER;
import static uac.imsp.clockingapp.Models.entity.Employee.EMPTY_PASSWORD;
import static uac.imsp.clockingapp.Models.entity.Employee.INVALID_FIRSTNAME;
import static uac.imsp.clockingapp.Models.entity.Employee.INVALID_LASTNAME;
import static uac.imsp.clockingapp.Models.entity.Employee.INVALID_MAIL;
import static uac.imsp.clockingapp.Models.entity.Employee.INVALID_NUMBER;
import static uac.imsp.clockingapp.Models.entity.Employee.INVALID_PASSWORD;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.util.IRegisterEmployeeController;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Planning;
import uac.imsp.clockingapp.Models.dao.PlanningManager;
import uac.imsp.clockingapp.Models.entity.Service;
import uac.imsp.clockingapp.Models.dao.ServiceManager;
import uac.imsp.clockingapp.View.util.IRegisterEmployeeView;

public class RegisterEmployeeController implements IRegisterEmployeeController
{

    IRegisterEmployeeView registerEmployeeView;


    public RegisterEmployeeController(IRegisterEmployeeView registerEmployeeView) {
        this.registerEmployeeView = registerEmployeeView;
    }

    @Override
    public String[] onLoad() {
       // String serviceLIst[]
        ServiceManager serviceManager = new ServiceManager((Context) registerEmployeeView);

       // serviceManager.open();

        //serviceManager.close();
        return serviceManager.getAllServices();
    }

    @Override
    public void onRegisterEmployee(String number, String lastname,
                                   String firstname, String gender, String birthdate,String mail,
                                   String username, String password, String passwordConfirm,
                                   String selectedService, int startTime, int endTime,
                                   byte[] picture,String type) {

        int registerCode;
        int n;
        Service service;
        Employee employee = null;
        byte[] qrCode;
        number=number.trim();
        lastname=lastname.trim();
        firstname=firstname.trim();
        mail=mail.trim();
        username=username.trim();
        password=password.trim();
        passwordConfirm=passwordConfirm.trim();

        PlanningManager planningManager;
        Planning planning;
        EmployeeManager employeeManager;
        try {
            n = Integer.parseInt(number);
            employee=new Employee(n,lastname,firstname,gender.charAt(0),
                    birthdate,mail,picture,username,password,type);
            registerCode=employee.isValid();


        }
        catch (NumberFormatException e){
            
            
            registerCode=EMPTY_NUMBER;

        }


       
   if( registerCode==EMPTY_NUMBER)
       registerEmployeeView.onRegisterEmployeeError("Matricule requis !");
   else if(registerCode==INVALID_NUMBER)
       registerEmployeeView.onRegisterEmployeeError("Matricule invalide !");
   else if(registerCode==EMPTY_LASTNAME)
       registerEmployeeView.onRegisterEmployeeError("Nom requis !");
   else if(registerCode==INVALID_LASTNAME)
       registerEmployeeView.onRegisterEmployeeError("Nom invalide !");
   else if(registerCode==EMPTY_FIRSTNAME)
       registerEmployeeView.onRegisterEmployeeError("Prénom requis !");
   else if(registerCode==INVALID_FIRSTNAME)
       registerEmployeeView.onRegisterEmployeeError("Prénom(s) invalide(s) !");
   else if(registerCode==EMPTY_MAIL)
       registerEmployeeView.onRegisterEmployeeError("Email requis !");
   else if(registerCode==EMPTY_BIRTHDATE)
       registerEmployeeView.onRegisterEmployeeError("Date de naissance requise !");

   else if(registerCode==INVALID_MAIL)
       registerEmployeeView.onRegisterEmployeeError("Email invalide !");
   else if(registerCode==EMPTY_PASSWORD)
       registerEmployeeView.onRegisterEmployeeError("Mot de passe requis !");
   else if(registerCode==INVALID_PASSWORD)
       registerEmployeeView.onRegisterEmployeeError("Mot de passe invalide !");
   else if(!Objects.equals(password, passwordConfirm))
       registerEmployeeView.onRegisterEmployeeError("Vérifier le mot de passe et resssayer !");
   else{

       employeeManager = new EmployeeManager((Context) registerEmployeeView);
       employeeManager.open();

       if(employeeManager.exists(employee))
           registerEmployeeView.onRegisterEmployeeError("Le matricule, le username " +
                   "ou l'email a été déjà attribué à un employé !");

       else {
           qrCode = generateQRCode(number);
           employee.setQRCode(qrCode);
           planning = new Planning(formatTime(startTime), formatTime(endTime));
           planningManager = new PlanningManager((Context) registerEmployeeView);
           planningManager.open();
           planningManager.create(planning);
           planningManager.close();

           service = new Service(selectedService);

           employeeManager.create(employee);
           employeeManager.update(employee, planning);
           employeeManager.update(employee, service);

           registerEmployeeView.onRegisterEmployeeSuccess("Employé enregistré avec succès");

       }
      // employeeManager.close();
   }

    }

    @Override
    public byte[] generateQRCode(String myText) {
        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code

            return getBytesFromBitmap(mBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    public  static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
            return stream.toByteArray();
        }
        return new byte[0];
    }
public String formatTime(int time) {
  if(time <10)
      return "0"+time+":"+"0";
  else
      return time+":"+"0";
}

}
