package uac.imsp.clockingapp.View.util;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.util.Result;

public interface IsearchEmployeeView {



    void onNoEmployeeFound(String message);
    void onEmployeeFound(ArrayList<Result> list);


    void onEmployeeSelected(String tilte);
    void onUpdate();
    void onDelete();
    void onPresenceReport();
    void onStatistics();
//    void onStart();


}
