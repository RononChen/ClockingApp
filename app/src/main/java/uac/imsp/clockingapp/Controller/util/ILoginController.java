package uac.imsp.clockingapp.Controller.util;

public interface ILoginController {
    void onLogin(String Username,String Password);
    void onLoad(int savedVersionCode,  int currentVersionCode);

}
