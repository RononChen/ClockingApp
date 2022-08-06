package uac.imsp.clockingapp.View.util;

public interface IsearchEmployeeView {



    void onNoEmployeeFound(String message);
    void onEmployeeFound();

    void onEmployeeSelected(String tilte);
    void onUpdate();
    void onDelete();
    void onPresenceReport();
    void onStatistics();


}
