package uac.imsp.clockingapp.Controller;

import android.graphics.Bitmap;

import java.text.ParseException;
import java.util.Hashtable;

public interface IUpdateEmployeeController {
    String[] onLoad(int number, Hashtable<String, Object> informations) throws ParseException;





    void onUpdateEmployee(String mail, String selectedService, int startTime,


                          int endTime, Bitmap picture, String type);


    void onReset();

}
