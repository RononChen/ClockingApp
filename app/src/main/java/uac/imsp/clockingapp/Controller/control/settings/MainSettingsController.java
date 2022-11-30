package uac.imsp.clockingapp.Controller.control.settings;

import android.content.Context;

import java.io.File;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.util.settings.IMainSettingsController;
import uac.imsp.clockingapp.View.util.settings.IMainSettingsView;

public class MainSettingsController implements IMainSettingsController {
	IMainSettingsView mainSettingsView;
	private Context context;
	public MainSettingsController(IMainSettingsView mainSettingsView){
		this.mainSettingsView=mainSettingsView;
		this.context= (Context) this.mainSettingsView;

	}

	@Override
	public void onShareApp() {
		String url="";
		String msg="Suivez facilement la présence de vos employés avec l'application ClockinApp" +
				" qui vous propose de nombreuses fonctionalités interessantes.\n" +
				"Pour télécharger l'application pour Android, suivez ce lien\n"+url;
		mainSettingsView.onShareApp(msg+url);

	}

	@Override
	public void onShareAppViaQRCode() {
		mainSettingsView.onShareAppViaQRCode();

	}

	@Override
	public void onOverview() {
		mainSettingsView.onOverview();

	}

	@Override
	public void onAppversin() {

	}

	@Override
	public void onUsersDoc() {

	}

	@Override
	public void onAccount() {
mainSettingsView.onAccount();
	}

	@Override
	public void onClearAppCache() {
		deleteCache();

	}

	@Override
	public void onName() {
		mainSettingsView.onName("Nom de l'entreprise","Editer le nom de l'entreprise",
				"Appliquer","Annuler");

	}

	@Override
	public void onPicture() {

	}

	@Override
	public void onEmail() {
		mainSettingsView.onEmail("Adresse mail de l'entreprise",
				"Editer l'adresse email de l'entreprise",
				"Appliquer","Annuler");

	}

	@Override
	public void onClocking() {

	}

	@Override
	public void onDescription() {
		mainSettingsView.onDescription("Description de l'entreprise",
				"Editer la description de l'entreprise",
				"Appliquer","Annuler");

	}


	@Override
	public void onUsername() {

	}

	@Override
	public void onPassword() {

	}

	@Override
	public void onAdd() {

	}

	@Override
	public void onDelete() {

	}

	@Override
	public void onUpdate() {

	}

	@Override
	public void onLanguague() {

	}

	@Override
	public void onDarkMode() {

	}

	@Override
	public void onHelp() {

	}

	@Override
	public void onReportProblem() {

	}
	public  void deleteCache() {
		try {
			File dir = context.getCacheDir();
			deleteDir(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean deleteDir(File dir) {
		boolean success;
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < Objects.requireNonNull(children).length; i++) {
				success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
			return dir.delete();
		} else if(dir!= null && dir.isFile()) {
			return dir.delete();
		} else {
			return false;
		}
	}
}
