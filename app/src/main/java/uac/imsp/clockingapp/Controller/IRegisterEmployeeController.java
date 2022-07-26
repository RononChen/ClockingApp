package uac.imsp.clockingapp.Controller;


public interface IRegisterEmployeeController {
    String[] onLoad();
    void onRegisterEmployee(String number,String lastname,String firstname,String Gender,
                            String Birthdate,String mail,
                            String Username,String Password,String passwordConfirm,
                            String service,String startTime,String endTime,byte[] picture);
    byte[] generateQRCode(String myText);
}

