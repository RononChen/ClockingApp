package uac.imsp.clockingapp.View;

public interface ILoginView {
    void onLoginSuccess(String message);
        void onLoginError(String message);
    void onFirstRun();
    void onNormalRun();
    void onUpgrade();
}
