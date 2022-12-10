package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Objects;

import dao.ServiceManager;
import entity.Service;
import uac.imsp.clockingapp.Controller.util.IServicesController;
import uac.imsp.clockingapp.Controller.util.ServiceResuslt;
import uac.imsp.clockingapp.View.util.IServicesView;

public class ServicesController  implements IServicesController {
	private final IServicesView servicesView;
	private final Context context;
	private  Service currentService;

	public ServicesController(IServicesView servicesView){
		this.servicesView=servicesView;
		this.context=(Context) this.servicesView;
	}


	@Override
	public void onUpdate(Hashtable<Integer, String> hashtable) {
		servicesView.askConfirmUpdate();
		Enumeration<Integer> ids=hashtable.keys();
		int id;

		Service service;
		String s;

		ServiceManager serviceManager=new ServiceManager(context);
		serviceManager.open();
		while (ids.hasMoreElements())
		{
			id=ids.nextElement();
			s=hashtable.get(id);
			if(Objects.equals(s, "")) {
				servicesView.onUpdateError(0,id );
				break;
			}
			service=new Service(s);
			if(serviceManager.exists(service))
			{
				servicesView.onUpdateError(1,id);

				break;
			}
			service=new Service(id);
			serviceManager.update(service,s);
		}
		serviceManager.close();



	}

	@Override
	public void onCancell() {
		servicesView.askConfirmCancel();

	}

	@Override
	public void onDelete(String serviceName) {
		this.currentService =new Service(serviceName);
		servicesView.askConfirmDelete();


	}

	@Override
	public void onDeleteConfirm(boolean confirmed) {

		if(confirmed)
		{
			ServiceManager serviceManager=new ServiceManager(context);
			serviceManager.open();
			if(!serviceManager.exists(currentService))
				servicesView.onDeleteError(0);
			else if(serviceManager.hasEmployee(currentService))
			servicesView.onDeleteError(1);
			else {
				serviceManager.delete(currentService);
				servicesView.onDeleteSucessful();
			}


			serviceManager.close();
		}

	}

	@Override
	public void onStart() {
		int id;
		String name;
		//String service;
		ArrayList<ServiceResuslt> list= new ArrayList<>();
		ServiceResuslt result;


		Service[] services;
		ServiceManager serviceManager;

		serviceManager=new ServiceManager(context);
		serviceManager.open();

		services = serviceManager.getAllService();
		serviceManager.close();

		if(services.length==0)
			servicesView.onNoServiceFound();
		else
		{
			for (Service service : services) {
				id = service.getId();
				name = service.getName();

				result = new ServiceResuslt(id );
				result.setName(name);
				list.add(result);
			}
			servicesView.onServiceFound(list);


		}



	}

	@Override
	public void check(String oldService, String newService) {

		if(!Objects.equals(oldService, newService))
			servicesView.onServiceEdited();
			}

	@Override
	public void onAddService(String service) {
		Service service1=new Service(service);
		ServiceManager serviceManager=new ServiceManager(context);
		serviceManager.open();
		if(service.equals(""))
			servicesView.onAddServiceError(0);
		else if(serviceManager.exists(service1))
			servicesView.onAddServiceError(1);
		else
		{
			serviceManager.create(service1);
			servicesView.onServiceAdded();
		}
		serviceManager.close();


	}
}
