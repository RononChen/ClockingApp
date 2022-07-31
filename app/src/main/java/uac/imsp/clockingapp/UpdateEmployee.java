package uac.imsp.clockingapp;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.IUpdateEmployeeController;
import uac.imsp.clockingapp.Controller.UpdateEmployeeController;
import uac.imsp.clockingapp.View.ConfirmDialog;
import uac.imsp.clockingapp.View.IUpdateEmployeeView;
import uac.imsp.clockingapp.View.ToastMessage;


public class UpdateEmployee extends AppCompatActivity
                           implements View.OnClickListener,
                           AdapterView.OnItemSelectedListener,
        NumberPicker.OnValueChangeListener , NumberPicker.Formatter,
        DialogInterface.OnClickListener,
        IUpdateEmployeeView {
    private EditText Email;
    private String selectedService, SelectedType;
    private Spinner spinnerTypes, spinnerServices;
    private ImageView image;
    private Bitmap picture;
    private int Start, End;
    private ConfirmDialog confirmDialog,d;
    private Hashtable<String, Object> informations =null;
    IUpdateEmployeeController updateEmployeePresenter;
    private ToastMessage Toast;

    public UpdateEmployee() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        updateEmployeePresenter = new UpdateEmployeeController(this);
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
            updateEmployeePresenter.onUpdateEmployee(toString(Email), selectedService,
                    Start, End, picture, SelectedType);
         else if(v.getId()==R.id.register_reset_button) {
             updateEmployeePresenter.onReset();
        }
         else if(v.getId()==R.id.register_picture_button)

        imageChooser();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (view.getId() == R.id.register_service)
            //get the selected service
            selectedService = String.valueOf(spinnerServices.getSelectedItem());
        else if (view.getId() == R.id.register_type)
            SelectedType = String.valueOf(spinnerTypes.getSelectedItem());



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initView() throws ParseException {
        String[] employeTypes = getResources().getStringArray(R.array.employee_types);
        String[] services = updateEmployeePresenter.onLoad(1, informations);

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
        Button reset = findViewById(R.id.register_reset_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        RadioGroup gender = findViewById(R.id.register_gender);


        spinnerServices = findViewById(R.id.register_service);
        spinnerTypes = findViewById(R.id.register_type);

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
        Email.setText(Objects.requireNonNull(informations.get("email")).toString());
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

       // Listeners
        update.setOnClickListener(this);
        reset.setOnClickListener(this);
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
        Toast = new ToastMessage(this,message);
        Toast.show();

    }

    @Override
    public void onNothingChanged(String message) {
        Toast = new ToastMessage(this,message);
        Toast.show();

    }

    @Override
    public void onReset(String message) {
        //We stop the activity
        UpdateEmployee.this.finish();
        // And we start it
        startActivity(getIntent());

    }

    @Override
    public void onUpdateEmployeeError(String message) {
        Toast = new ToastMessage(this,message);
        Toast.show();

    }

    @Override
    public void askConfirmUpdate(String pos, String neg, String title, String message) {
        confirmDialog = new ConfirmDialog(UpdateEmployee.this,title,message);
        confirmDialog.setPositiveButton(pos,this);
        confirmDialog.setNegativeButton(neg,this);
        confirmDialog.show();
         confirmDialog.setIcon(R.drawable.ic_baseline_delete_forever_24);
    }

    @Override
    public void askConfirmReset(String pos, String neg, String title, String message) {

        d = new ConfirmDialog(UpdateEmployee.this,title,message);
        d.setPositiveButton(pos,this);
        d.setNegativeButton(neg,this);
        d.show();
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
        if (picker.getId() == R.id.register_planning_start_choose)
            Start = newVal;
        else if (picker.getId() == R.id.register_planning_end_choose)
            End = newVal;
    }

    public String toString(EditText e) {
        return e.getText().toString();

    }

    public void selectSpinnerItemByValue(Spinner spinner, String value) {
        int i;
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) spinner.getAdapter();
        for (i = 0; i < adapter.getCount(); i++)
            if (adapter.getItem(i).equals(value)) {spinner.setSelection(i);
                break;
            }


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(dialog== confirmDialog) {

            if (which == BUTTON_NEGATIVE) {
                //The activity is stopped and restarted
                UpdateEmployee.this.finish();
                startActivity(getIntent());
            }
        }
        else if(dialog==d)
        {

            if(which==BUTTON_POSITIVE)
                UpdateEmployee.this.finish();
            startActivity(getIntent());
        }


    }
}