package uac.imsp.clockingapp.Controller;

import java.text.ParseException;
import java.util.Hashtable;

public interface IDeleteEmployeeController {
    String[] onLoad(int number, Hashtable<String, Object> informations) throws ParseException;




    void onDeleteEmployee();




}



