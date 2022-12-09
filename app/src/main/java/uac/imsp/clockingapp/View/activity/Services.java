package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.control.ServicesController;
import uac.imsp.clockingapp.Controller.util.ServiceResuslt;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IServicesView;
import uac.imsp.clockingapp.View.util.ServiceListViewAdapter;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class Services extends AppCompatActivity implements IServicesView,
		View.OnClickListener, AdapterView.OnItemLongClickListener,
		AdapterView.OnItemClickListener {
	private ListView list;
	ServiceListViewAdapter adapter;
	ServicesController servicesPresenter;
	ArrayList<ServiceResuslt> arrayList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
		initView();
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
		cancel.setOnClickListener(this);
		apply.setOnClickListener(this);
		list=findViewById(R.id.service_list);
		list.setVisibility(View.GONE);
		list.setOnItemLongClickListener(this);
		list.setOnItemClickListener(this);

	}

	@Override
	public void onUpdate() {

	}

	@Override
	public void askConfirmDelete() {
		String message="",title="",
				pos=getString(R.string.yes),
				neg=getString(R.string.no);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(pos, (dialog, which) -> {
					servicesPresenter.onDeleteConfirm(true);
					Services.this.finish();
					startActivity(getIntent());
				})
				.setNegativeButton(neg, (dialog, which) -> {

					servicesPresenter.onDeleteConfirm(false);
						Services.this.finish();
					startActivity(getIntent());
				});
		AlertDialog alert = builder.create();
		alert.setTitle(title);
		alert.show();

	}

	@Override
	public void onUpdateError(int errorNumber, int serviceIndex) {
		//switch (errorNumber)

	}

	@Override
	public void onDeleteSucessful() {
		String message=getString(R.string.service_deleted);
		new ToastMessage(this,message);

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
	public void onServiceEdit() {

	}

	@Override
	public void onServiceChanged(int id) {
		int position=getTheMatchingPosition(id);

		((ServiceListViewAdapter) list.getAdapter()).
				getServiceNameEditText(position).setBackgroundColor(Color.YELLOW);


	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		servicesPresenter.onDelete(( adapter.getItem(position)).getName());
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
	public int getTheMatchingPosition(int id){

		for(int position = 0;position<arrayList.size(); position++) {
			if(arrayList.get(position).getId()==id)
				return position;
		}
		return -1;
	}
}