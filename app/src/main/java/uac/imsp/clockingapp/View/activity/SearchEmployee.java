package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.control.SearchEmployeeController;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IsearchEmployeeView;
import uac.imsp.clockingapp.View.util.ListViewAdapter;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class SearchEmployee extends AppCompatActivity
    implements IsearchEmployeeView,
        AdapterView.OnItemClickListener,View.OnClickListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener,
        DialogInterface.OnClickListener {
    private  ListView list;
    ListViewAdapter adapter;
    private String Data;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    SearchEmployeeController searchEmployeePresenter;

    private Intent intent;
    private int  Number;
    private  androidx.appcompat.widget.SearchView search;
    public SearchEmployee(){
        searchEmployeePresenter=new SearchEmployeeController(this);
    }

private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);
        initView();
        //SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
          //search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Result result = (Result) adapter.getItem(position);
        Number= result.getNumber();
        searchEmployeePresenter.onEmployeeSelected(Number);
    }

    @Override
    public void onNoEmployeeFound(String message) {

        new ToastMessage(this,message);



    }

    @Override
    public void onEmployeeFound(ArrayList<Result> employeeList) {
        adapter = new ListViewAdapter(this, employeeList);
        this.list.setAdapter(adapter);
        this.list.setVisibility(View.VISIBLE);
        search.clearFocus();
        search.setFocusable(false);
    }

    @Override
    public void onEmployeeSelected(String tilte) {

        String  [] options=getResources().getStringArray(R.array.actions);
        builder=new AlertDialog.Builder(this);
        builder.setTitle(tilte);
        builder.setItems(options,this);
        dialog=builder.create();
        dialog.show();



    }

    @Override
    public void onUpdate() {
        intent=new Intent(SearchEmployee.this,UpdateEmployee.class);
        intent.putExtra("ACTION_NUMBER",Number);

        startActivity(intent);
    }
    //android:background="@drawable/search_view_rounded"
    @Override
    public void onDelete() {
        intent=new Intent(SearchEmployee.this,DeleteEmployee.class);
        intent.putExtra("ACTION_NUMBER",Number);
        startActivity(intent);

    }

    @Override
    public void onPresenceReport() {
        intent=new Intent(SearchEmployee.this,ConsultPresenceReport.class);
        intent.putExtra("ACTION_NUMBER",Number);
        startActivity(intent);

    }

    @Override
    public void onStatistics() {
        intent=new Intent(SearchEmployee.this,ConsultStatistics.class);
        intent.putExtra("NUMBER",Number);
        startActivity(intent);

    }

    public void initView(){
        search = findViewById(R.id.search);
        search.setOnSearchClickListener(this);
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list=findViewById(R.id.employee_list);
        list.setVisibility(View.GONE);
        search.setOnQueryTextListener(this);

      // search.setIconified(true);
         // search.setSubmitButtonEnabled(false);
         // search.setQueryHint("Rechercher par nom prémom ou service");


       // search.setFocusable(true);
        //search.requestFocusFromTouch();

       // search.clearFocus();

       // search.onActionViewExpanded();

        list.setOnItemClickListener(this);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Data=search.getQuery().toString().trim();
        searchEmployeePresenter.onSearch(Data);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

       // Data=newText;
        //search.setQuery(Data,false);
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        searchEmployeePresenter.onOptionSelected(which);

    }

    @Override
    public void onClick(View v) {
        search.setIconifiedByDefault(false);

    }
}