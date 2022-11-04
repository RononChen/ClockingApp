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
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.UpdateEmployeeController;
import uac.imsp.clockingapp.Controller.util.IUpdateEmployeeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IUpdateEmployeeView;
import uac.imsp.clockingapp.View.util.ToastMessage;


public class UpdateEmployee extends AppCompatActivity
                           implements View.OnClickListener,
                           AdapterView.OnItemSelectedListener,
        NumberPicker.OnValueChangeListener , NumberPicker.Formatter,
        IUpdateEmployeeView {

    private EditText Email;
    private TextView Programm;
    private String selectedService, selectedType;
    private Spinner spinnerTypes, spinnerServices;
    private ImageView image;
    private Bitmap picture;
    private Integer Start, End;
    private boolean pictureUpdated,planningUpdated;

    IUpdateEmployeeController updateEmployeePresenter;

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
        if (v.getId() == R.id.update_button) {
            Log.d("Start : ", Start + "");
            Log.d("End : ", End + "");
            updateEmployeePresenter.onUpdateEmployee(toString(Email), selectedService,

                    Start, End, picture, selectedType);
        } else if (v.getId() == R.id.register_reset_button) {
            updateEmployeePresenter.onReset();
        } else if (v.getId() == R.id.register_picture_button)

            imageChooser();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view.getId() == R.id.register_service) {

            selectedService = spinnerServices.getItemAtPosition(position).toString();


        } else if (view.getId() == R.id.register_type)
            selectedType = spinnerTypes.getSelectedItem().toString();


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initView() throws ParseException {

        int position;
        int actionNumber = getIntent().getIntExtra("ACTION_NUMBER", 1);

        Hashtable<String, Object> informations = new Hashtable<>();
        String[] employeTypes = getResources().getStringArray(R.array.employee_types);
        String[] services = updateEmployeePresenter.onLoad(actionNumber, informations);
        selectedService = (String) informations.get("service");
        selectedType = (String) informations.get("type");
        EditText number = findViewById(R.id.register_number);
        EditText lastname = findViewById(R.id.register_lastname);
        Email = findViewById(R.id.register_email);
        EditText firstname = findViewById(R.id.register_firstname);
        EditText username = findViewById(R.id.register_username);
        EditText birthdate = findViewById(R.id.register_birthdate);
        NumberPicker start = findViewById(R.id.register_planning_start_choose);
        NumberPicker end = findViewById(R.id.register_planning_end_choose);
        start.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        end.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        if (informations.containsKey("start") && informations.containsKey("end")) {
            Start = (Integer) informations.get("start");
            End = (Integer) informations.get("end");
            start.setValue(Start);
            end.setValue(End);
        }
        Programm=findViewById(R.id.prog);
        Programm.setText(programm());

        ImageView previewImage = findViewById(R.id.register_preview_image);
        Button update = findViewById(R.id.update_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        RadioGroup gender = findViewById(R.id.register_gender);

        gender.getChildAt(0).setEnabled(false);
        gender.getChildAt(1).setEnabled(false);

        spinnerServices = findViewById(R.id.register_service);
        spinnerTypes = findViewById(R.id.register_type);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, services);

        dataAdapterR.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);
        position=dataAdapterR.getPosition(selectedService);
        spinnerServices.setSelection(position);

        dataAdapterR = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, employeTypes);

        dataAdapterR.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(dataAdapterR);
        position=dataAdapterR.getPosition(selectedType);
        spinnerTypes.setSelection(position);
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
                        //picture is updated if selected bitmap is ot null
                        pictureUpdated= selectedImageBitmap != null;

                    }
                }
            });

    @Override
    public void onSomethingchanged(String message) {
         new ToastMessage(this, message);


    }

    @Override
    public void onNothingChanged(String message) {
        new ToastMessage(this, message);


    }



    @Override
    public void onUpdateEmployeeError(String message) {
         new ToastMessage(this, message);



    }

    @Override
    public void askConfirmUpdate(String pos, String neg, String title, String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(pos, (dialog, which) -> {

                    new ToastMessage(UpdateEmployee.this,"Confirmé");
                    updateEmployeePresenter.onConfirmResult(true,
                            pictureUpdated,planningUpdated );
                    UpdateEmployee.this.finish();
                    startActivity(getIntent());
                })
                .setNegativeButton(neg, (dialog, which) -> {
                    new ToastMessage(UpdateEmployee.this,"Annulé");

                    updateEmployeePresenter.onConfirmResult(false,pictureUpdated ,planningUpdated );
                    UpdateEmployee.this.finish();
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
        planningUpdated=true;

        if(picker.getId()==R.id.register_planning_start_choose
                && 5<newVal && 10>newVal) {
            Start = newVal;
            Programm.setText(programm());
        }

        else if (picker.getId()==R.id.register_planning_end_choose && 15<newVal && 20>newVal) {
            End = newVal;
            Programm.setText(programm());
        }
    }

    public String toString(EditText e) {
        return e.getText().toString();

    }

    public void selectSpinnerItemByValue(Spinner spinner, String value, int resID) {
        int i;
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, resID,
                        android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);

        if (value != null) {
            i = adapter.getPosition(value);
            spinner.setSelection(i);
        }


    }
    public String programm(){
        String str="";
        if(Start<10)
            str="0";
        str+=Start+":00-" ;
        if (End<10)
            str+="0";
        str+=End+":00";
        return str;

    }
}