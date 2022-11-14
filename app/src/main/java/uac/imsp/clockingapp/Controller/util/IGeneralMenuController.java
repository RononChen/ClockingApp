package uac.imsp.clockingapp.Controller.util;

public interface IGeneralMenuController {


    void onSearchEmployeeMenu(int currentUser);
        void onUpdateEmployeeMenu(int currentUser);
    void onDeleteEmployeeMenu(int currentUser);
    boolean onRegisterEmployeeMenu(int currentUser);
    void onClocking();
    void onConsultatisticsMenu(int currentUser);
    void onConsultPresenceReport();
    void onExit();
    //void onConfirmResult();
    //void onHome();
}

