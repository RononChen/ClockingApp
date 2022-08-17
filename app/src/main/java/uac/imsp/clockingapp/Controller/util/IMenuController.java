package uac.imsp.clockingapp.Controller.util;

public interface IMenuController {

    void onSearchEmployeeMenu(int currentUser);
        void onUpdateEmployeeMenu(int currentUser);
    void onDeleteEmployeeMenu(int currentUser);
    void onRegisterEmployeeMenu(int currentUser);
    void onClocking();
    void onConsultatisticsMenu(int currentUser);
    void onConsultPresenceReport();
    void onExit();
    //void onConfirmResult();
    //void onHome();
}

