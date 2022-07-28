package uac.imsp.clockingapp.View;

public interface IClockInOutView{
    //Welcome message on load
    void onLoad(String welcome);

    //Message de succès de pointage
    void onClockingSuccessful(String message);
    //Message d'erreur de pointage ou employe non retrouvé
    void onClockingError(String message);

}

