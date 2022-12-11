package uac.imsp.clockingapp.View.util;

public interface ILoginView {
    void onLoginSuccess(int number);
    void onSimpleEmployeeLogin();
    void onLoginError(String message);
    void onClocking();
    void onPasswordError(String message);
    void onUsernameError(String username);
    void onShowHidePassword();
    void onMaxAttempsReached(String message);
    void askWish();
    void onPositiveResponse();
    void onNegativeResponse();

}
