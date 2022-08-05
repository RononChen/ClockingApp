package uac.imsp.clockingapp.View.util;

public interface ILoginView {
    void onLoginSuccess(String message);
        void onLoginError(String message);
    void onFirstRun();
    void onNormalRun();
    void onUpgrade();
}
