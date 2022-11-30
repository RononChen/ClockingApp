package uac.imsp.clockingapp.View.util.settings;

public interface IMainSettingsView {
	void onShareApp(String msg);
	void onShareAppViaQRCode();
	void onAppcacheCleared();
	void onName(String title,String msg,String pos,String neg);
	void onEmail(String title,String msg,String pos,String neg);
	void onDescription(String title,String msg,String pos,String neg);

	void onOverview();
	void onAccount();
	void onClocking();
}
