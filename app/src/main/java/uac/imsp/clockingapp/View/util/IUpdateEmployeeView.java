package uac.imsp.clockingapp.View.util;

public interface IUpdateEmployeeView {
    void onSomethingchanged(String message);
    void onNothingChanged(String message);
    void onUpdateEmployeeError(String message);
    void askConfirmUpdate(String pos, String neg, String title, String message);


}


