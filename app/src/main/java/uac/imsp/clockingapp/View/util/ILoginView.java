package uac.imsp.clockingapp.View.util;

public interface ILoginView {
    void onLoginSuccess(String message,int number);
    void onLoginError(String message);
    void onFirstRun();
    void onNormalRun();
    void onUpgrade();
    void onClocking();
    void onPasswordError(String message);
    void onUsernameError(String username);
    void onShowHidePassword();
    void onMaxAttempsReached(String message);
     void askWhich(String pos, String neg, String title, String message);
}
