package uac.imsp.clockingapp.View.util;

import java.util.Hashtable;

public interface IConsultStatisticsView {
        //Message to print,tile of message
    void onConsultStatistics(String title,String message);
    //Case of no service found
    void onNoServiceFound(String message);
    void onServiceFound(String[] serviceName);
    //We'll ask the period
    void onServiceSelected(String title,String message);
    void askTime(String message);
    void onEndDateSelected(Hashtable<String,Double> rowSet);
    void onEmployeeSelected(String message,int mounth);
}


