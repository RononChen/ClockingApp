package uac.imsp.clockingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import uac.imsp.clockingapp.Controller.IRegisterEmployeeController;
import uac.imsp.clockingapp.Controller.RegisterEmployeeController;
import uac.imsp.clockingapp.View.IRegisterEmployeeView;


public class RegisterEmployee extends AppCompatActivity
        implements IRegisterEmployeeView,
        View.OnClickListener,
        RadioGroup.OnCheckedChangeListener ,

        AdapterView.OnItemSelectedListener
{
    IRegisterEmployeeController registerEmployeePresenter;

    private  RadioGroup Type;
    private EditText Lastname, Firstname, Email, Username, Password, PasswordConfirm,Number, Birthdate;
    private String Birth;
    DatePickerDialog picker;
    private ImageView PreviewImage;
    private byte[] Picture;
    private String gend;
    private String SelectedService;
    private  final Spinner spinnerServices = findViewById(R.id.register_service);
    private int Start=6,End=18;
    private  TextView StartTime,EndTime;



    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);

        initView();



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
                            Picture = getBytesFromBitmap(selectedImageBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        PreviewImage.setImageBitmap(
                                selectedImageBitmap);

                    }
                }
            });

    @SuppressLint("DefaultLocale")
        @Override
    public void onClick(View v) {
        String time;
        if(v.getId()==R.id.register_planning_start)
        {
            if(Start<11)
                Start++;


            else if (Start==11)
                Start=8;

            time=toString(StartTime).replaceFirst("[0-9]+",String.valueOf(Start));
            StartTime.setText(time);


        }
        else if(v.getId()==R.id.register_planning_end)
        {
            if(End<18)
                Start++;
            else if (End==18)
                End=13;
            time=toString(EndTime).replaceFirst("[0-9]+",String.valueOf(End));
            StartTime.setText(time);
        }
        else if (v.getId() == R.id.register_picture_button)
            imageChooser();
        else if (v.getId() == R.id.register_button ) {
            registerEmployeePresenter.onRegisterEmployee(toString(Number), toString(Lastname),
                                        toString(Firstname),gend,toString(Email),Birth,toString(Username),
                    toString(Password),toString(PasswordConfirm),SelectedService,
                    String.valueOf(Start),String.valueOf(End),Picture,"");



        }
        else if (v.getId() == R.id.register_reset_button)

            resetInput();
        else if (v.getId() == R.id.register_birthdate) {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

            picker = new DatePickerDialog(RegisterEmployee.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {

                        Birthdate.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year1));
                        Birth = "" + year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    },
                    year, month, day);
            picker.show();
        }

    }

        public  byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }
    public void resetInput() {
        Number.setText("");
        Lastname.setText("");
        Firstname.setText("");
        Email.setText("");
        Username.setText("");
        Password.setText("");
        PasswordConfirm.setText("");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        gend = (checkedId == R.id.register_boy) ? "M" : "F";

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SelectedService = String.valueOf(spinnerServices.getSelectedItem());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    public void onRegisterEmployeeSuccess(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterEmployeeError(String message) {
      Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public String toString(EditText e){
        return  e.getText().toString();

    }
    //public String toString()
   public String toString(TextView tv){
        return  tv.getText().toString();
   }
    public void initView(){
        registerEmployeePresenter = new RegisterEmployeeController(this);
        String[] services = registerEmployeePresenter.onLoad();

        String time=toString(StartTime)+"H";
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);
        StartTime.setText(String.format("%s  %s", StartTime, time));
        time=toString(EndTime)+"H";
        EndTime.setText(String.format("%s  %s", EndTime, time));
                spinnerServices.setOnItemSelectedListener(this);

        StartTime=findViewById(R.id.register_planning_start);
        EndTime=findViewById(R.id.register_planning_end);
        Number = findViewById(R.id.register_number);
        Lastname = findViewById(R.id.register_lastname);
        Firstname = findViewById(R.id.register_firstname);
        RadioGroup gender = findViewById(R.id.register_gender);
        Birthdate = findViewById(R.id.register_birthdate);
        Email = findViewById(R.id.register_email);
        Username = findViewById(R.id.register_username);
        Password = findViewById(R.id.register_password);
        Type=findViewById(R.id.register_gender);//to be changed
        PasswordConfirm = findViewById(R.id.register_password_confirm);
        PreviewImage = findViewById(R.id.register_preview_image);
        Button register = findViewById(R.id.register_button);
        Button reset = findViewById(R.id.register_reset_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        gender.setOnCheckedChangeListener(this);
        Birthdate.setOnClickListener(this);
        register.setOnClickListener(this);
        reset.setOnClickListener(this);
        selectPicture.setOnClickListener(this);
        StartTime.setOnClickListener(this);
        StartTime.setOnClickListener(this);
    }
}