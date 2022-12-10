package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Hashtable;

import uac.imsp.clockingapp.Controller.control.ServicesController;
import uac.imsp.clockingapp.Controller.util.ServiceResuslt;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IServicesView;
import uac.imsp.clockingapp.View.util.ServiceListViewAdapter;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Services extends AppCompatActivity implements IServicesView,
		View.OnClickListener, AdapterView.OnItemLongClickListener,
		AdapterView.OnItemClickListener, TextWatcher {
	private ListView list;
	ServiceListViewAdapter adapter;
	ServicesController servicesPresenter;
	ArrayList<ServiceResuslt> arrayList;
	final  Button addService=findViewById(R.id.add_service);
	final EditText editService=findViewById(R.id.edit_service);
	EditText serviceListViewItemEditText;
	int position;
	View view;
	ViewGroup parent;
	String oldService,newService;
	Hashtable<Integer, String> hashtable=new Hashtable<>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
		initView();
		servicesPresenter=new ServicesController(this);
		servicesPresenter.onStart();

	}

	@Override
	public void onNoServiceFound() {

		list.setVisibility(View.GONE);



	}

	@Override
	public void onServiceFound(ArrayList<ServiceResuslt> list) {
		adapter = new ServiceListViewAdapter(this, list);
		this.list.setAdapter(adapter);
		this.list.setVisibility(View.VISIBLE);
		arrayList=list;


	}

	public void initView(){
		final Button cancel = findViewById(R.id.cancel);
	final 	Button apply = findViewById(R.id.apply);

	addService.setOnClickListener(this);
	addService.setSelectAllOnFocus(true);
		cancel.setOnClickListener(this);
		apply.setOnClickListener(this);
		list=findViewById(R.id.service_list);
		list.setVisibility(View.GONE);
		list.setOnItemLongClickListener(this);
		list.setOnItemClickListener(this);
		editService.setOnClickListener(this);

	}


	@Override
	public void askConfirmDelete() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.delete_confirmation_message))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes), (dialog, which) ->
						servicesPresenter.onDeleteConfirm(true))
				.setNegativeButton(getString(R.string.no), (dialog, which) ->

					servicesPresenter.onDeleteConfirm(false));
		AlertDialog alert = builder.create();
		alert.setTitle(getString(R.string.delete_confirmation));
		alert.show();

	}

	@Override
	public void onUpdateError(int errorNumber, int serviceIndex) {
		int position=	getTheMatchingPosition(serviceIndex);
		switch (errorNumber)
		{

			case 0:
				new ToastMessage(this,
						getString(R.string.service_invalid));

			adapter.getServiceNameEditText(position,view,parent).
					setBackgroundColor(Color.RED);

			break;

			case 1:
				new ToastMessage(this,getString(R.string.service_already_exists));
				adapter.getServiceNameEditText(position,view,parent).
						setBackgroundColor(Color.YELLOW);
				break;
			default:
				break;

		}

	}

	@Override
	public void onDeleteSucessful() {

		new ToastMessage(this,getString(R.string.service_deleted));
		servicesPresenter.onStart();

	}

	@Override
	public void onDeleteError(int errorNumber) {
		switch (errorNumber){
			case 0:
			new ToastMessage(this,getString(R.string.no_such_service));
				break;
			case 1:
				new ToastMessage(this,getString(R.string.delete_not_possible));
				break;
			default:
				break;
		}

	}


	@Override
	public void onServiceEdited() {
		serviceListViewItemEditText.setBackgroundColor(Color.BLUE);
		serviceListViewItemEditText.setText(newService);
	hashtable.put(adapter.getItem(position).getId(),newService);

	}

	@Override
	public void onAddServiceError(int errorNumber) {
switch (errorNumber){
	case 0:
		new ToastMessage(this,getString(R.string.service_invalid));
		addService.setBackgroundColor(Color.RED);
		break;
	case 1:
		new ToastMessage(this,getString(R.string.service_already_exists));
		addService.setBackgroundColor(Color.YELLOW);
		break;
	default:
		break;
}
	}

	@Override
	public void onServiceAdded() {
		new ToastMessage(this,getString(R.string.notify_service_added));
		editService.setText("");
		servicesPresenter.onStart();
	}

	@Override
	public void askConfirmCancel() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.confirm_update_service))
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes), (dialog, which) ->
						servicesPresenter.onStart())
				.setNegativeButton(getString(R.string.no), (dialog, which) -> {
				});
		AlertDialog alert = builder.create();
		alert.setTitle(getString(R.string.confirm_update_service_title));
		alert.show();

	}

	@Override
	public void askConfirmUpdate() {


		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.confirm_update_service)
				.setCancelable(false)
				.setPositiveButton(getString(R.string.yes), (dialog, which) ->
						servicesPresenter.onStart())
				.setNegativeButton(getString(R.string.no), (dialog, which) -> {
				});
		AlertDialog alert = builder.create();
		alert.setTitle(R.string.confirm_update_service_title);
		alert.show();


	}

	@Override
	public void onClick(@NonNull View v) {

		if(v.getId()==R.id.add_service)
			servicesPresenter.onAddService(addService.getText().toString().trim());
		else if(v.getId()==R.id.cancel)
			servicesPresenter.onCancell();
		else if(v.getId()==R.id.apply)
			servicesPresenter.onUpdate(hashtable);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		servicesPresenter.onDelete(( adapter.getItem(position)).getName());
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	serviceListViewItemEditText=adapter.getServiceNameEditText(position,
			view,parent);
	serviceListViewItemEditText.setSelectAllOnFocus(true);
	serviceListViewItemEditText.addTextChangedListener(this);
		this.parent=parent;
		this.view=view;
	this.position=position;


	}
	public int getTheMatchingPosition(int id){

		for(int position = 0;position<arrayList.size(); position++) {
			if(arrayList.get(position).getId()==id)
				return position;
		}
		return -1;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
oldService=s.toString();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(@NonNull Editable s) {
		newService=s.toString();
		servicesPresenter.check(oldService,newService);
		
	}
}