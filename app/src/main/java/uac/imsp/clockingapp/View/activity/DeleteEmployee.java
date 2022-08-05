package uac.imsp.clockingapp.View.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.DeleteEmployeeController;
import uac.imsp.clockingapp.Controller.util.IDeleteEmployeeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IDeleteEmployeeView;
import uac.imsp.clockingapp.View.util.ToastMessage;



public class DeleteEmployee extends AppCompatActivity
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        NumberPicker.OnValueChangeListener , NumberPicker.Formatter,
        IDeleteEmployeeView {

    private EditText Email;
    private String selectedService, selectedType;
    private Spinner spinnerTypes, spinnerServices;
    private ImageView image;
    private Bitmap picture;
    private Integer Start, End;
    private AlertDialog.Builder builder;

    IDeleteEmployeeController deleteEmployeePresenter;
    private ToastMessage Toast;

    public DeleteEmployee() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
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
        if (v.getId() == R.id.update_button) {
            Log.d("Start : ", Start + "");
            Log.d("End : ", End + "");
            deleteEmployeePresenter.onDeleteEmployee(toString(Email), selectedService,

                    Start, End, picture, selectedType);
        } else if (v.getId() == R.id.register_reset_button) {
            deleteEmployeePresenter.onReset();
        } else if (v.getId() == R.id.register_picture_button)

            imageChooser();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (view.getId() == R.id.register_service) {

            selectedService = String.valueOf(spinnerServices.getSelectedItem());


        } else if (view.getId() == R.id.register_type)
            selectedType = String.valueOf(spinnerTypes.getSelectedItem());


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initView() throws ParseException {

        Hashtable<String, Object> informations = new Hashtable<>();
        String[] employeTypes = getResources().getStringArray(R.array.employee_types);
        String[] services = deleteEmployeePresenter.onLoad(1, informations);
        selectedService = services[0];
        selectedType = employeTypes[0];
        EditText number = findViewById(R.id.register_number);
        EditText lastname = findViewById(R.id.register_lastname);
        Email = findViewById(R.id.register_email);
        EditText firstname = findViewById(R.id.register_firstname);
        EditText username = findViewById(R.id.register_username);
        EditText birthdate = findViewById(R.id.register_birthdate);
        NumberPicker start = findViewById(R.id.register_planning_start_choose);
        NumberPicker end = findViewById(R.id.register_planning_end_choose);

        ImageView previewImage = findViewById(R.id.register_preview_image);
        Button update = findViewById(R.id.update_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        RadioGroup gender = findViewById(R.id.register_gender);


        spinnerServices = findViewById(R.id.register_service);
        spinnerTypes = findViewById(R.id.register_type);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, services);

        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);

        dataAdapterR = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, employeTypes);

        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(dataAdapterR);
        initNumberPicker(start, 6, 9);
        initNumberPicker(end, 16, 19);

        previewImage.setImageBitmap((Bitmap) informations.get("picture"));
        number.setText(Objects.requireNonNull(informations.get("number")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
        firstname.setText(Objects.requireNonNull(informations.get("firstname")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
        Email.setText(Objects.requireNonNull(informations.get("email")).toString());
        username.setText(Objects.requireNonNull(informations.get("username")).toString());

        birthdate.setText(Objects.requireNonNull(informations.get("birthdate")).toString());
        if (Objects.equals(informations.get("gender"), 'F'))
            gender.setId(R.id.register_girl);
        selectSpinnerItemByValue(spinnerServices,
                (String) informations.get("service"),
                R.id.register_service);

        selectSpinnerItemByValue(spinnerTypes,
                (String) informations.get("type"),
                R.id.register_type);

        if (informations.containsKey("start") && informations.containsKey("end")) {
            Start = (Integer) informations.get("start");
            End = (Integer) informations.get("end");
            start.setValue(Start);
            end.setValue(End);
        }


        //Not updatable
        number.setEnabled(false);
        firstname.setEnabled(false);
        lastname.setEnabled(false);
        birthdate.setEnabled(false);
        username.setEnabled(false);
        gender.setEnabled(false);

        // Listeners
        update.setOnClickListener(this);
        selectPicture.setOnClickListener(this);
        spinnerServices.setOnItemSelectedListener(this);
        spinnerTypes.setOnItemSelectedListener(this);
        //Formatters
        start.setFormatter(this);
        end.setFormatter(this);
    }

    public void initNumberPicker(NumberPicker n, int min, int max) {
        if (n != null) {
            n.setMinValue(min);
            n.setMaxValue(max);
            n.setWrapSelectorWheel(true);
            n.setOnValueChangedListener(this);
        }
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                            picture = selectedImageBitmap;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        image.setImageBitmap(
                                selectedImageBitmap);

                    }
                }
            });

    @Override
    public void onSomethingchanged(String message) {
        Toast = new ToastMessage(this, message);

    }

    @Override
    public void onNothingChanged(String message) {
        Toast = new ToastMessage(this, message);


    }


    @Override
    public void onDeleteEmployeeError(String message) {
        Toast = new ToastMessage(this, message);
        Toast.show();


    }

    @Override
    public void askConfirmUpdate(String pos, String neg, String title, String message) {


        builder=new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(pos, (dialog, which) -> {

                    Toast = new ToastMessage(DeleteEmployee.this,"Confirmé");
                    deleteEmployeePresenter.onConfirmResult(true);
                    DeleteEmployee.this.finish();
                    startActivity(getIntent());
                })
                .setNegativeButton(neg, (dialog, which) -> {

                    Toast = new ToastMessage(DeleteEmployee.this,"Annulé");

                    deleteEmployeePresenter.onConfirmResult(false);
                    DeleteEmployee.this.finish();
                    startActivity(getIntent());
                });

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();



    }



    @Override
    public String format(int value) {
        String str;
        str = String.valueOf(value);
        if (value < 10)
            str = "0" + value;
        return str;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        if (picker.getId() == R.id.register_planning_start_choose && 5 < newVal && newVal < 10)
            Start = newVal;

        else if (picker.getId() == R.id.register_planning_end_choose && 15 < newVal && newVal < 19)
            End = newVal;
    }

    public String toString(EditText e) {
        return e.getText().toString();

    }

    public void selectSpinnerItemByValue(Spinner spinner, String value, int resID) {
        int i;
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, resID,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (value != null) {
            i = adapter.getPosition(value);
            spinner.setSelection(i);
        }


    }

}