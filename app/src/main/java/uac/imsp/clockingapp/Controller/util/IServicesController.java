package uac.imsp.clockingapp.Controller.util;

import java.util.Hashtable;

public interface IServicesController {

	void onUpdate(Hashtable<Integer ,String > hashtable);
	void onCancell();
	void onDelete(String serviceName);
	void onDeleteConfirm(boolean confirmed);

	void onStart();
	void check(int id, String newService);
}
