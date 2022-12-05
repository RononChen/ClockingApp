package uac.imsp.clockingapp.View.util;

import java.util.Hashtable;

public interface IConsultStatisticsByEmployeeView {

    void onStart(String date);
    void onMonthSelected(Hashtable<Character,Float> statistics);


}


