package uac.imsp.clockingapp.View.util;


public interface IRegisterEmployeeView {
    void onRegisterEmployeeSuccess();
    void onRegisterEmployeeError(String message);
    void onShowHidePassword(int viewId,int eyeId);
    void sendEmail(String [] to, String subject, String message,
                   String qrCodeFileName);

}

