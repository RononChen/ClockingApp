package uac.imsp.clockingapp.View;

public interface IUpdateEmployeeView {
    void onSomethingchanged(String message);
    void onNothingChanged(String message);
    void onReset(String message);

    void onUpdateEmployeeError(String message);
    void askConfirmUpdate(String pos,String neg,String title,String message);
    void askConfirmReset(String pos,String neg,String title,String message);
}

