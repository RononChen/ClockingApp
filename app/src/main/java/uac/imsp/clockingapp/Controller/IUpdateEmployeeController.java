package uac.imsp.clockingapp.Controller;

public interface IUpdateEmployeeController {
    String[] onLoad(String[] serviceList);

        void onNumberSelected(int number);

    void onUpdateEmployee(String selectedService,String startTime,
                          String endTime,byte[] picture);


}
