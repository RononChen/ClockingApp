package uac.imsp.clockingapp.View.util;

import java.util.Hashtable;

public interface IConsultStatisticsByServiceView {

        //Message to print,tile of message
    //void onConsultStatistics(String title,String message);
    //Case of no service found
    void onNoServiceFound();
    void onServiceFound(Hashtable<String, Integer> rowSet);

}



