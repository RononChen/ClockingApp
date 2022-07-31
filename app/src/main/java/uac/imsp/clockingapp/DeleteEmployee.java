package uac.imsp.clockingapp;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static uac.imsp.clockingapp.UpdateEmployee.selectSpinnerItemByValue;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.DeleteEmployeeController;
import uac.imsp.clockingapp.Controller.IDeleteEmployeeController;
import uac.imsp.clockingapp.View.ConfirmDialog;
import uac.imsp.clockingapp.View.IDeleteEmployeeView;
import uac.imsp.clockingapp.View.ToastMessage;


public class DeleteEmployee extends AppCompatActivity

        implements View.OnClickListener,
        DialogInterface.OnClickListener,
        IDeleteEmployeeView {
   
    IDeleteEmployeeController deleteEmployeePresenter;


    public DeleteEmployee() {
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);
        deleteEmployeePresenter = new DeleteEmployeeController(this);
        try {
            initView();
        } catch (ParseException e) {
            e.printStackTrace();

        }



    }

    @Override
    public void onClick(View v) {

        //bouton modifier
        if (v.getId() == R.id.update_button)
            deleteEmployeePresenter.onDeleteEmployee();
    }


    public void initView() throws ParseException {
         Hashtable<String, Object> informations = new Hashtable<>();
        String[] employeTypes = getResources().getStringArray(R.array.employee_types);
        String[] services = deleteEmployeePresenter.onLoad(55, informations);


        EditText number = findViewById(R.id.register_number);
        EditText lastname = findViewById(R.id.register_lastname);
        EditText email = findViewById(R.id.register_email);
        EditText firstname = findViewById(R.id.register_firstname);
        EditText username = findViewById(R.id.register_username);
        EditText birthdate = findViewById(R.id.register_birthdate);
        NumberPicker start = findViewById(R.id.register_planning_start_choose);
        NumberPicker end = findViewById(R.id.register_planning_end_choose);

        ImageView previewImage = findViewById(R.id.register_preview_image);
        Button delete= findViewById(R.id.update_button);
        RadioGroup gender = findViewById(R.id.register_gender);


        Spinner spinnerServices = findViewById(R.id.register_service);
        Spinner spinnerTypes = findViewById(R.id.register_type);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);

        dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeTypes);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(dataAdapterR);
        initNumberPicker(start, 6, 9);
        initNumberPicker(end, 16, 19);

        previewImage.setImageBitmap((Bitmap) informations.get("picture"));
        number.setText(Objects.requireNonNull(informations.get("number")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
        firstname.setText(Objects.requireNonNull(informations.get("firstname")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
        email.setText(Objects.requireNonNull(informations.get("email")).toString());
        username.setText(Objects.requireNonNull(informations.get("username")).toString());
        birthdate.setText(Objects.requireNonNull(informations.get("birhdate")).toString());
        if (Objects.equals(informations.get("gender"), 'F'))
            gender.setId(R.id.register_girl);
        selectSpinnerItemByValue(spinnerServices,
                Objects.requireNonNull(informations.get("service")).toString());

        selectSpinnerItemByValue(spinnerTypes, Objects.requireNonNull(informations.get("type")).toString());
        start.setValue(Integer.parseInt((String) Objects.requireNonNull(informations.get("start"))));
        end.setValue(Integer.parseInt((String) Objects.requireNonNull(informations.get("end"))));


        //Not updatable
        number.setEnabled(false);
        firstname.setEnabled(false);
        lastname.setEnabled(false);
        birthdate.setEnabled(false);
        username.setEnabled(false);
        gender.setEnabled(false);
        spinnerServices.setEnabled(false);
        spinnerTypes.setEnabled(false);
        email.setEnabled(false);
        start.setEnabled(false);
        end.setEnabled(false);
        

        // Listeners
        delete.setOnClickListener(this);

    }

    public void initNumberPicker(NumberPicker n, int min, int max) {
        if (n != null) {
            n.setMinValue(min);
            n.setMaxValue(max);
            n.setWrapSelectorWheel(true);
        }
    }
    @Override
    public void onDeleteSucessfull(String message) {
        ToastMessage toast = new ToastMessage(this, message);
        toast.show();

    }

    @Override
    public void askConfirmDelete(String pos, String neg, String title, String message) {
        ConfirmDialog confirmDialog = new ConfirmDialog(DeleteEmployee.this, title, message);
        confirmDialog.setPositiveButton(pos,this);
        confirmDialog.setNegativeButton(neg,this);
        confirmDialog.show();
        confirmDialog.setIcon(R.drawable.ic_baseline_delete_forever_24);
    }





    @Override
    public void onClick(DialogInterface dialog, int which) {


            if (which == BUTTON_NEGATIVE) {
                //The activity is stopped and restarted
                DeleteEmployee.this.finish();
                startActivity(getIntent());
            }


    }
}