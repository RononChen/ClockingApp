package uac.imsp.clockingapp.View.util;

public interface IMenuView {
    void onSearchEmployeeMenuError(String message);
    void onUpdateEmployeeMenuError(String message);
    void onDeleteEmployeeMenuError(String message);
    void onRegisterEmployeeMenuError(String message);
    void onConsultatisticsMenuError(String message);

    void onSearchEmployeeMenuSuccessfull();
    void onUpdateEmployeeMenuSuccessfull();
    void onDeleteEmployeeMenuSucessfull();
    void onRegisterEmployeeMenuSuccessful();
    void onConsultatisticsMenuSuccessful();

    void onClocking();
    void onConsultPresenceReport();


}
