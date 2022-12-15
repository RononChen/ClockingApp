package uac.imsp.clockingapp.View.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import uac.imsp.clockingapp.Controller.control.UpdateEmployeeController;
import uac.imsp.clockingapp.Controller.util.IUpdateEmployeeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IUpdateEmployeeView;
import uac.imsp.clockingapp.View.util.ToastMessage;


public class UpdateEmployee extends AppCompatActivity
                           implements View.OnClickListener,
                           AdapterView.OnItemSelectedListener,
        NumberPicker.OnValueChangeListener , NumberPicker.Formatter,
        IUpdateEmployeeView
{


    private EditText Email;
    private String outgoingMail;
    private TextView Programm;
    private String selectedService, selectedType, provisionalService,provisionalType;
    private  Spinner spinnerTypes, spinnerServices;
    private CircleImageView image;
    private Bitmap picture;
    CheckBox monday, tuesday, wednesday, thursday, friday, satursday, sunday;
    private Integer Start, End;
    private String outgoingType,outgoingService;
    private boolean pictureUpdated,planningUpdated,
    typeUpdated,serviceUpdated,
            emailUpdated,someThingIsUpdated=false;
    private byte[] WorkDays;
    private CheckBox[]myTable;

    private byte[] oldWorkDays,outgoingWorkDays;
    private int outgoingStart,outgoingEnd;
    boolean notice;


    IUpdateEmployeeController updateEmployeePresenter;

    public UpdateEmployee() {
    }
    public void  retrieveSharedPreferences(){
        String PREFS_NAME="MyPrefsFile";
        SharedPreferences preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        notice=preferences.getBoolean("notifyDelete",true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        retrieveSharedPreferences();
        updateEmployeePresenter = new UpdateEmployeeController(this);

        initView();


    }

    @Override
    public void onClick(@NonNull View v) {

        //bouton modifier
        if (v.getId() == R.id.update_button)
        {
            if(!Objects.equals(outgoingMail, Email.getText().toString().trim()))
                emailUpdated=true;
            if(!Objects.equals(outgoingService, provisionalService))
                serviceUpdated=true;
            if(!Objects.equals(outgoingType, provisionalType))
                typeUpdated=true;
            if(Start!=outgoingStart||outgoingEnd!=End||
                    WorkDays!=outgoingWorkDays)
                planningUpdated=true;



            updateEmployeePresenter.onUpdateEmployee(
                    emailUpdated, serviceUpdated,

                    planningUpdated,pictureUpdated, typeUpdated);

        } else if (v.getId() == R.id.register_reset_button) {
            updateEmployeePresenter.onReset();
            provisionalType=selectedType;
            provisionalService=selectedService;
        } else if (v.getId() == R.id.register_picture_button)

            imageChooser();


    }

    public void sendEmail(String[] to, String subject, String message) {

    Intent emailIntent=new Intent(Intent.ACTION_SEND);
    emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
    emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
    emailIntent.putExtra(Intent.EXTRA_TEXT,message);
    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    startActivity(Intent.createChooser(emailIntent, "Envoyer avec"));
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerServices.getId() == R.id.register_service) {
          provisionalService=  parent.getItemAtPosition(position).toString();




        } else if (spinnerTypes.getId() == R.id.register_type)
          provisionalType= (String) parent.getItemAtPosition(position);


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initView()  {

        int position;
        int actionNumber = getIntent().getIntExtra("ACTION_NUMBER", 1);

        Hashtable<String, Object> informations = new Hashtable<>();
        String[] employeTypes = getResources().getStringArray(R.array.employee_types);
        String[] services = new String[0];
        try {
            services = updateEmployeePresenter.onLoad(actionNumber, informations);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        selectedService = (String) informations.get("service");
        outgoingService = (String) informations.get("service");
        selectedType = (String) informations.get("type");
        outgoingType=(String) informations.get("type");
        provisionalService=selectedService;
        provisionalType=selectedType;
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

        if (informations.containsKey("start") &&
                informations.containsKey("end")&&
        informations.containsKey("workDays"))
        {
            Start = (Integer) informations.get("start");
            outgoingStart=Start;
            End = (Integer) informations.get("end");
            outgoingEnd=End;
            WorkDays= (byte[]) informations.get("workDays");
            outgoingWorkDays=WorkDays;
            oldWorkDays=WorkDays;
            start.setValue(Start);
            end.setValue(End);

        }
        Programm=findViewById(R.id.prog);
        Programm.setText(programm());

        image = findViewById(R.id.register_preview_image);
        Button update = findViewById(R.id.update_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        RadioGroup gender = findViewById(R.id.register_gender);

        gender.getChildAt(0).setFocusable(false);
        gender.getChildAt(1).setFocusable(false);
        gender.getChildAt(0).setClickable(false);
        gender.getChildAt(1).setClickable(false);

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
if(informations.get("picture")!=null)
        image.setImageBitmap((Bitmap) informations.get("picture"));
        number.setText(Objects.requireNonNull(informations.get("number")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
        firstname.setText(Objects.requireNonNull(informations.get("firstname")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
       outgoingMail=Objects.requireNonNull(informations.get("email")).toString();
        Email.setText(outgoingMail);
        username.setText(Objects.requireNonNull(informations.get("username")).toString());


        birthdate.setText(Objects.requireNonNull(informations.get("birthdate")).toString());
        if (Objects.equals(informations.get("gender"), 'F'))
            gender.setId(R.id.register_girl);
         monday=findViewById(R.id.monday);
                tuesday=findViewById(R.id.tuesday);
                wednesday=findViewById(R.id.wednesday);
                thursday=findViewById(R.id.thursday);
                friday=findViewById(R.id.friday);
                satursday=findViewById(R.id.satursday);
                sunday=findViewById(R.id.sunday);
        checkWorkdays(); //check workdays boxes

        //Not updatable
        number.setFocusable(false);
        number.setLongClickable(false);
        firstname.setFocusable(false);
        firstname.setLongClickable(false);
        lastname.setFocusable(false);
        lastname.setLongClickable(false);

        birthdate.setFocusable(false);
        birthdate.setLongClickable(false);

        if (Objects.requireNonNull(informations.get("birthdate")).toString().equals(""))
        {
            findViewById(R.id.tv).setVisibility(View.GONE);
            birthdate.setVisibility(View.GONE);

        }

        username.setFocusable(false);
        username.setLongClickable(false);
        gender.setFocusable(false);
        gender.setClickable(false);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                        //picture is updated if selected bitmap is not null
                        pictureUpdated= selectedImageBitmap != null;

                    }
                }
            });
    public void checkWorkdays(){
//to set checked the workdays and unchecked the others days
        myTable=new CheckBox[7];
        myTable[0]=monday;
        myTable[1]=tuesday;
        myTable[2]=wednesday;
        myTable[3]=thursday;
        myTable[4]=friday;
        myTable[5]=satursday;
        myTable[6]=sunday;
        int i;
        for(i=0;i<7;i++)
        {

            myTable[i].setChecked(WorkDays[i] == 'T');

        }
    }

    @Override
    public void onSomethingchanged() {
         new ToastMessage(this, getString(R.string.update_succcessful));
        sendEmail(new String[]{Email.getText().toString().trim()},
                getString(R.string.notification_title),getString(R.string.notification));


    }

    @Override
    public void onNothingChanged() {
        new ToastMessage(this,getString(R.string.noting_changed) );


    }



    @Override
    public void onUpdateEmployeeError(int errorNumber) {
        switch (errorNumber){

            case 0:
                new ToastMessage(this, getString(R.string.mail_required));
                break;
            case 1:
                new ToastMessage(this, getString(R.string.mail_invalid));
                break;
            default:
                break;

        }




    }

    @Override
    public void askConfirmUpdate() {
        String pos=getString(R.string.yes);
        String neg=getString(R.string.no);
        String title=getString(R.string.confirmation);
        String message=getString(R.string.confirmation_update);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(pos, (dialog, which) -> {

                    //new ToastMessage(UpdateEmployee.this,"Confirmé");
                    updateEmployeePresenter.onUpdateEmployee(Email.getText().toString(),
                            selectedService,Start,End,WorkDays,picture,selectedType);


                })
                .setNegativeButton(neg, (dialog, which) -> {
                    new ToastMessage(UpdateEmployee.this,"Annulé");

                    //updateEmployeePresenter.onConfirmResult(false,pictureUpdated ,planningUpdated );
                    UpdateEmployee.this.finish();
                    startActivity(getIntent());
                });

                AlertDialog alert = builder.create();
                alert.setTitle(title);
                alert.show();



    }
    public byte[] workDays(){
        byte[] tab =new byte[7];//[]{'T','A','A','A','A','A','A'};
        int i;
        for(i=0;i<7;i++)
        {
            if(myTable[i].isChecked())
                tab[i]='T'; //for true
            else
                tab[i]='F';


        }


        return tab;
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
    public void onValueChange(@NonNull NumberPicker picker, int oldVal, int newVal) {
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

    public String toString(@NonNull EditText e) {
        return e.getText().toString();

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