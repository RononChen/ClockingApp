package uac.imsp.clockingapp.Controller.util;

import java.util.Hashtable;

public interface IServicesController {
	void onUpdate(Hashtable<Integer ,String > hashtable);
	void onCancel(Hashtable<Integer ,String > hashtable);
	void onDelete(String serviceName);
	void onDeleteConfirm(boolean confirmed);
	void onStart();
	void check(String oldService, String newService);
	void onAddService(String service);

	void onUpdateConfirmed();
}
