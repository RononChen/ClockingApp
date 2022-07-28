package uac.imsp.clockingapp.View;

public interface IDeleteEmployeeView {

    void askConfirm(String pos,String neg,String title,String message);
    void onDeleteSucessful(String message);
    void onDeleteCancelled(String message);


}

