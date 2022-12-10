package uac.imsp.clockingapp.Controller.control;

import static entity.Employee.EMPTY_FIRSTNAME;
import static entity.Employee.EMPTY_LASTNAME;
import static entity.Employee.EMPTY_MAIL;
import static entity.Employee.EMPTY_NUMBER;
import static entity.Employee.EMPTY_PASSWORD;
import static entity.Employee.EMPTY_USERNAME;
import static entity.Employee.INVALID_FIRSTNAME;
import static entity.Employee.INVALID_LASTNAME;
import static entity.Employee.INVALID_MAIL;
import static entity.Employee.INVALID_NUMBER;
import static entity.Employee.INVALID_PASSWORD;
import static entity.Employee.INVALID_USERNAME;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import dao.EmployeeManager;
import dao.PlanningManager;
import dao.ServiceManager;
import entity.Employee;
import entity.Planning;
import entity.Service;
import uac.imsp.clockingapp.Controller.util.IRegisterEmployeeController;
import uac.imsp.clockingapp.View.util.IRegisterEmployeeView;

public class RegisterEmployeeController implements IRegisterEmployeeController
{

    IRegisterEmployeeView registerEmployeeView;
   private ServiceManager serviceManager;
   private final Context context;
   private String fileName;
    FileOutputStream qrCodeFileOutputStream;
    File file;



    public RegisterEmployeeController(IRegisterEmployeeView registerEmployeeView) {
        this.registerEmployeeView = registerEmployeeView;
        this.context= (Context) this.registerEmployeeView;
    }

    public RegisterEmployeeController(IRegisterEmployeeView registerEmployeeView,
                                      Context context) {
        this.registerEmployeeView = registerEmployeeView;
        this.context=context;
    }

    @Override
    public String[] onLoad() {


       // String serviceLIst[]
        serviceManager = new ServiceManager(context);

        serviceManager.open();
        String[] services = serviceManager.getAllServices();
        serviceManager.close();

        return services;
    }

    @Override
    public void onRegisterEmployee(String number, String lastname,
                                   String firstname, String gender, String birthdate, String mail,
                                   String username, String password, String passwordConfirm,
                                   String selectedService, int startTime, int endTime,
                                   byte[] picture, String type, @NonNull byte[] workdays) {
        int nb_workdays=0;
        for(byte elt:workdays)
        {
            if(elt=='T')
                nb_workdays++;
        }

String gend;
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
       registerEmployeeView.onRegisterEmployeeError(0);
   else if(registerCode==INVALID_NUMBER)
       registerEmployeeView.onRegisterEmployeeError(1);
   else if(registerCode==EMPTY_LASTNAME)
       registerEmployeeView.onRegisterEmployeeError(2);
   else if(registerCode==INVALID_LASTNAME)
       registerEmployeeView.onRegisterEmployeeError(3);
   else if(registerCode==EMPTY_FIRSTNAME)
       registerEmployeeView.onRegisterEmployeeError(4);
   else if(registerCode==INVALID_FIRSTNAME)
       registerEmployeeView.onRegisterEmployeeError(5);
   else if(registerCode==EMPTY_MAIL)
       registerEmployeeView.onRegisterEmployeeError(6);
   else if(registerCode==INVALID_MAIL)
       registerEmployeeView.onRegisterEmployeeError(7);
   else if(registerCode==EMPTY_USERNAME)
       registerEmployeeView.onRegisterEmployeeError(8);
   else if (registerCode==INVALID_USERNAME)
       registerEmployeeView.onRegisterEmployeeError(9);
   else if(registerCode==EMPTY_PASSWORD)
       registerEmployeeView.onRegisterEmployeeError(10);
   else if(registerCode==INVALID_PASSWORD)
       registerEmployeeView.onRegisterEmployeeError(11);
   else if(!Objects.equals(password, passwordConfirm))

       registerEmployeeView.onRegisterEmployeeError(12);

   else if(nb_workdays==0)
       registerEmployeeView.onRegisterEmployeeError(13);
   else{
       employeeManager = new EmployeeManager(context);
       employeeManager.open();

       if(employeeManager.registrationNumberExists(employee))

           registerEmployeeView.onRegisterEmployeeError(14);
       else if(employeeManager.emailExists(employee))
           registerEmployeeView.onRegisterEmployeeError(15);
       else if(employeeManager.usernameExists(employee))
       registerEmployeeView.onRegisterEmployeeError(16);


       else {


           gend = employee.getGender() == 'M' ? "Monsieur" : "Madame";

           try {

               fileName = "qr_code_" + employee.getRegistrationNumber() + "_" +
                       employee.getFirstname() + "_" + employee.getLastname() +
                       ".png";
               file = new File(context.getFilesDir(), fileName);

               assert (file.createNewFile());

           } catch (IOException e) {
               e.printStackTrace();
           }


           qrCode = generateQRCode(number);//byte array
           try {
               qrCodeFileOutputStream = new FileOutputStream(file);
               qrCodeFileOutputStream.write(qrCode);
               qrCodeFileOutputStream.flush();
               qrCodeFileOutputStream.close();
           } catch (IOException e) {
               e.printStackTrace();

           }
           planning = new Planning(formatTime(startTime), formatTime(endTime),workdays);
           planningManager = new PlanningManager(context);
           planningManager.open();
           planningManager.create(planning);
           planningManager.close();



           service = new Service(selectedService);
           serviceManager=new ServiceManager(context);
           serviceManager.open();
           serviceManager.searchService(service);
           serviceManager.close();

           employee.setPassword(employee.getPassword());
           employeeManager.create(employee);
           employeeManager.update(employee, planning);
           employeeManager.update(employee, service);


           if (employee.getPicture() != null)
               employeeManager.update(employee, employee.getPicture());

           registerEmployeeView.onRegisterEmployeeSuccess();
           registerEmployeeView.sendEmail( new String[]{employee.getMailAddress()},
                   fileName,employee.getLastname(),employee.getFirstname()
                   ,employee.getUsername() ,
                   employee.getPassword(), gend
                   );
       }
       employeeManager.close();


       }

   }



    public ServiceManager getServiceManager() {
        return serviceManager;
    }


    @Override
    public byte[] generateQRCode(String regNumber) {
        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(regNumber, BarcodeFormat.QR_CODE,
                    400, 400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
            //creating bitmap of code

            return getBytesFromBitmap(mBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public void onShowHidePassword(int viewId,int eyeId) {
registerEmployeeView.onShowHidePassword(viewId, eyeId);
    }

    public   byte[] getBytesFromBitmap(Bitmap bitmap) {

        if (bitmap != null) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,70,stream);
            return stream.toByteArray();
        }
        return new byte[0];

    }
public String formatTime(int time) {
  if(time <10)
      return "0"+time+":"+"00";
  else

      return time+":"+"00";
}

}
