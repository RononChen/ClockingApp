package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import uac.imsp.clockingapp.Controller.control.SearchEmployeeController;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.CustomListAdapter;
import uac.imsp.clockingapp.View.util.IsearchEmployeeView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class SearchEmployee extends AppCompatActivity
    implements IsearchEmployeeView,
        AdapterView.OnItemClickListener {
    private List<Result> employeeList;
    private  ListView list;
    private Result result;

    SearchEmployeeController searchEmployeePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);
        initView();

        employeeList= searchEmployeePresenter.onSearch("AKO");


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object object = list.getItemAtPosition(position);
        result  = (Result) object;
        Toast.makeText(SearchEmployee.this, "Selected :" + " " + result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoEmployeeFound(String message) {

        new ToastMessage(this,message);

    }
    public void initView(){
        ListView list=findViewById(R.id.employee_list);
        list.setAdapter(new CustomListAdapter(this,employeeList));
        list.setOnItemClickListener(this);

    }
}