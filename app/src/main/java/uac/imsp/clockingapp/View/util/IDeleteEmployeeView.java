package uac.imsp.clockingapp.View.util;

public interface IDeleteEmployeeView {

    void onSomethingchanged(String message);
    void onNothingChanged(String message);
    void onDeleteEmployeeError(String message);

    void askConfirmUpdate(String pos, String neg, String title, String message);


}


