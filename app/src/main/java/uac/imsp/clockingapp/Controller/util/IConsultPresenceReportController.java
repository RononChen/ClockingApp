package uac.imsp.clockingapp.Controller.util;
import java.text.ParseException;
import java.util.Hashtable;

public interface IConsultPresenceReportController {

    //The controller get the regNumber of the employee concerned
    void onConsultPresenceReport(int number);

    //when he selects the month the controller can compute the presence report
     void onMonthSelected(int month) throws ParseException;
     void onConsultOwnStatistics() ;
      Hashtable < Hashtable<Integer,String>,Character> onOwnMonthSelected() throws ParseException;




}

