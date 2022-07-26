package uac.imsp.clockingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.DeleteEmployeeController;
import uac.imsp.clockingapp.Controller.IDeleteEmployeeController;
import uac.imsp.clockingapp.View.IDeleteEmployeeView;

public class DeleteEmployee extends AppCompatActivity
                            implements View.OnClickListener ,
        AdapterView.OnItemSelectedListener,
        IDeleteEmployeeView {

private Button Delete;
private String [] Numbers,services;
private  EditText Lastname,Firstname,Gender;
private ImageView Picture;
private AlertDialog.Builder confirmDialog;
private int Number=1;
//private final Spinner spinnerNumbers = findViewById(R.id.update_number_list);

private String SelectedNumber;
private IDeleteEmployeeController deleteEmployeePresenter;
private int selectedNumber;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
        String [] infos;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);
        initView();
        deleteEmployeePresenter=new DeleteEmployeeController(this);



    }

    @Override
    public void onClick(View v) {
    deleteEmployeePresenter.onDelete(selectedNumber);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //selectedNumber = Integer.parseInt(String.valueOf(spinnerNumbers.getSelectedItem()));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void initView(){
        Picture.findViewById(R.id.delete_picture);
        Lastname.findViewById(R.id.delete_lastname);
        Firstname.findViewById(R.id.delete_firstname);
        Gender.findViewById(R.id.delete_gender);
        Delete=findViewById(R.id.delete);
        Delete.setOnClickListener(this);
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Numbers);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerNumbers.setAdapter(dataAdapterR);
        //spinnerNumbers.setOnItemSelectedListener(this);
        Delete.setOnClickListener(this);
    }


    @Override
    public void askConfirm(String pos,String neg,String title,String message) {
         confirmDialog = new AlertDialog.Builder(this);
         confirmDialog.setTitle(title);
         confirmDialog.setMessage(message);
         confirmDialog.setPositiveButton(pos, (dialog, which) -> {

         deleteEmployeePresenter.onDeleteConfirmed();
         finish();

         });

         confirmDialog.setNegativeButton(neg, (dialog, which) -> {

             finish();

         });

    }

    @Override
    public void onDeleteSucessful(String message) {
      Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCancelled(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }


}