package uac.imsp.clockingapp.Controller;

import static uac.imsp.clockingapp.Models.Employee.EMPTY_FIRSTNAME;
import static uac.imsp.clockingapp.Models.Employee.EMPTY_LASTNAME;
import static uac.imsp.clockingapp.Models.Employee.EMPTY_MAIL;
import static uac.imsp.clockingapp.Models.Employee.EMPTY_NUMBER;
import static uac.imsp.clockingapp.Models.Employee.EMPTY_PASSWORD;
import static uac.imsp.clockingapp.Models.Employee.INVALID_FIRSTNAME;
import static uac.imsp.clockingapp.Models.Employee.INVALID_LASTNAME;
import static uac.imsp.clockingapp.Models.Employee.INVALID_MAIL;
import static uac.imsp.clockingapp.Models.Employee.INVALID_NUMBER;
import static uac.imsp.clockingapp.Models.Employee.INVALID_PASSWORD;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.Models.Planning;
import uac.imsp.clockingapp.Models.PlanningManager;
import uac.imsp.clockingapp.Models.Service;
import uac.imsp.clockingapp.Models.ServiceManager;
import uac.imsp.clockingapp.View.IRegisterEmployeeView;

public class RegisterEmployeeController implements  IRegisterEmployeeController
{

    IRegisterEmployeeView registerEmployeeView;

    public RegisterEmployeeController(IRegisterEmployeeView registerEmployeeView) {
        this.registerEmployeeView = registerEmployeeView;
    }

    @Override
    public String[] onLoad() {
       // String serviceLIst[]
        ServiceManager serviceManager = new ServiceManager((Context) registerEmployeeView);

        serviceManager.open();
        String[] serviceList =serviceManager.getAllServices();
        serviceManager.close();
        return serviceList;
    }

    @Override
    public void onRegisterEmployee(String number, String lastname,
                                   String firstname, String gender, String birthdate,String mail,
                                   String username, String password, String passwordConfirm,
                                   String selectedService, String startTime, String endTime,
                                   byte[] picture) {
        int registerCode;
        int n= number.equals("")?-1:Integer.parseInt(number);
        Service service;
        PlanningManager planningManager;
        Planning planning;
        EmployeeManager employeeManager;

        Employee employee=new Employee(n,lastname,firstname,gender.charAt(0),
                birthdate,mail,picture,username,password);
   registerCode=employee.isValid();
   if(registerCode==EMPTY_NUMBER)
       registerEmployeeView.onRegisterEmployeeError("Matricule requis !");
   else if(registerCode==INVALID_NUMBER)
       registerEmployeeView.onRegisterEmployeeError("Matricule invalide !");
   else if(registerCode==EMPTY_LASTNAME)
       registerEmployeeView.onRegisterEmployeeError("Nom requis !");
   else if(registerCode==INVALID_LASTNAME)
       registerEmployeeView.onRegisterEmployeeError("Nom invalide !");
   else if(registerCode==EMPTY_FIRSTNAME)
       registerEmployeeView.onRegisterEmployeeError("Prénom requis");
   else if(registerCode==INVALID_FIRSTNAME)
       registerEmployeeView.onRegisterEmployeeError("Prénom invalide");
   else if(registerCode==EMPTY_MAIL)
       registerEmployeeView.onRegisterEmployeeError("Email requis !");
   else if(registerCode==INVALID_MAIL)
       registerEmployeeView.onRegisterEmployeeError("Email invalide !");
   else if(registerCode==EMPTY_PASSWORD)
       registerEmployeeView.onRegisterEmployeeError("Mot de passe requis");
   else if(registerCode==INVALID_PASSWORD)
       registerEmployeeView.onRegisterEmployeeError("Mot de passe invalide");
   else if(!Objects.equals(password, passwordConfirm))
       registerEmployeeView.onRegisterEmployeeError("Vérifier le mot de passe et resssayer !");
   else{
       planning=new Planning(startTime,endTime);
       planningManager = new PlanningManager((Context) registerEmployeeView);
       planningManager.open();
       planningManager.create(planning);
       planningManager.close();

       service = new Service(selectedService);

       employeeManager = new EmployeeManager((Context) registerEmployeeView);
       employeeManager.open();

       employeeManager.create(employee);
       employeeManager.update(employee,planning);
       employeeManager.update(employee,service);
       employeeManager.storeQRCode(employee);
       employeeManager.close();
       registerEmployeeView.onRegisterEmployeeSuccess("Employé enregistré avec succès");

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


}
