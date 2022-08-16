package uac.imsp.clockingapp.View.util;

import java.util.Hashtable;

public interface IConsultStatisticsByEmployeeView {

    void onStart(String message);
    void onMonthSelected(Hashtable<Character,Float> statistics);


}


