package uac.imsp.clockingapp.View.util;

public interface IChangePasswordView {
    void onSuccess(String message);
    void onWrongPassword(String message);
    void onStart(String username);

}
