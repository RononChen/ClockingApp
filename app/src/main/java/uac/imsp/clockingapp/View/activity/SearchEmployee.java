package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import uac.imsp.clockingapp.Controller.control.SearchEmployeeController;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IsearchEmployeeView;
import uac.imsp.clockingapp.View.util.ListViewAdapter;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class SearchEmployee extends AppCompatActivity
    implements IsearchEmployeeView,
        AdapterView.OnItemClickListener,
        SearchView.OnQueryTextListener,
        DialogInterface.OnClickListener {
    private List<Result> employeeList;
    private  ListView list;
    ListViewAdapter adapter;
    private Result result;
    private String Data;
    AlertDialog.Builder builder;
    SearchEmployeeController searchEmployeePresenter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);
        initView();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int number;


        result= (Result) adapter.getItem(position);
        number=result.getNumber();
        searchEmployeePresenter.onEmployeeSelected(number);


    }

    @Override
    public void onNoEmployeeFound(String message) {

        new ToastMessage(this,message);


    }

    @Override
    public void onEmployeeFound() {

        int i;
        employeeList=new ArrayList<>();
        ArrayList<Result> arrayList = new ArrayList<>();
        for (i=0;i <employeeList.size();i++) {
            result = new Result(employeeList.get(i).getNumber(),
                    employeeList.get(i).getName(),
                    employeeList.get(i).getService());

            arrayList.add(result);
        }
        adapter = new ListViewAdapter(this, arrayList);
        list.setAdapter(adapter);
        list.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmployeeSelected(String tilte) {

        String  [] options=getResources().getStringArray(R.array.actions);
        builder=new AlertDialog.Builder(this);
        builder.setTitle(tilte);
        builder.setItems(options,this);



    }

    @Override
    public void onUpdate() {
        intent=new Intent(SearchEmployee.this,UpdateEmployee.class);
        startActivity(intent);
    }

    @Override
    public void onDelete() {
        intent=new Intent(SearchEmployee.this,DeleteEmployee.class);
        startActivity(intent);

    }

    @Override
    public void onPresenceReport() {
        intent=new Intent(SearchEmployee.this,ConsultPresenceReport.class);
        startActivity(intent);
    }

    @Override
    public void onStatistics() {
        intent=new Intent(SearchEmployee.this,ConsultStatistics.class);
        startActivity(intent);

    }

    public void initView(){
        SearchView search = (SearchView) findViewById(R.id.search);
        list=findViewById(R.id.employee_list);
        list.setVisibility(View.GONE);
        search.setOnQueryTextListener(this);
        list.setOnItemClickListener(this);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        employeeList=searchEmployeePresenter.onSearch(Data);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Data=newText;
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        searchEmployeePresenter.onOptionSelected(which);

    }
}