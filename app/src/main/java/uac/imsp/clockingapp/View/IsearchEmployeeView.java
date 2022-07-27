package uac.imsp.clockingapp.View;

public interface IsearchEmployeeView {

    //Case an employee is found
    void onEmployeeFound(int number,String lastname,String firstname,byte[]picture,String service);
    //Case any employee isn't found

    void onNoEmployeeFound(String message);

}
