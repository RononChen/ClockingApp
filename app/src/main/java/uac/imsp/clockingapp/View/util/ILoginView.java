package uac.imsp.clockingapp.View.util;

public interface ILoginView {
    void onLoginSuccess(int number);
    void onLoginError(String message);
    void onFirstRun();
    void onNormalRun();
    void onUpgrade();
    void onClocking();
    void onPasswordError(String message);
    void onUsernameError(String username);
    void onShowHidePassword();
    void onMaxAttempsReached(String message);
    void askWish(String pos, String neg, String title, String message);
    void onPositiveResponse(String message);
    void onNegativeResponse(String message);

}
