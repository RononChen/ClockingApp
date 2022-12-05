package uac.imsp.clockingapp.View.util;

public interface IUpdateEmployeeView {
    void onSomethingchanged(String message);
    void onNothingChanged(String message);
    void onUpdateEmployeeError(String message);
    void askConfirmUpdate();


}


