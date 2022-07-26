package uac.imsp.clockingapp.Controller;

public interface IDeleteEmployeeController {
    String[] onLoad(String[] serviceList);
    void onDelete(int number);

    void onDeleteConfirmed();
    void onNumberSelected(int number);

}

