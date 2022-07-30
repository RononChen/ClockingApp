package uac.imsp.clockingapp.Controller;


public interface IRegisterEmployeeController {
    String[] onLoad();
    void onRegisterEmployee(String number,String lastname,String firstname,String Gender,
                            String Birthdate,String mail,
                            String Username,String Password,String passwordConfirm,
                            String service,int startTime,int endTime,byte[] picture,String type);
    byte[] generateQRCode(String myText);

}


