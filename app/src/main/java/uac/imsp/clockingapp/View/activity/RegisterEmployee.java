package uac.imsp.clockingapp.View.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import uac.imsp.clockingapp.Controller.control.RegisterEmployeeController;
import uac.imsp.clockingapp.Controller.util.IRegisterEmployeeController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IRegisterEmployeeView;


public class RegisterEmployee extends AppCompatActivity
        implements NumberPicker.OnValueChangeListener , NumberPicker.Formatter,
        IRegisterEmployeeView,
        View.OnClickListener,
        RadioGroup.OnCheckedChangeListener ,

        AdapterView.OnItemSelectedListener,
        TextWatcher

{
    IRegisterEmployeeController registerEmployeePresenter;

    private EditText Lastname, Firstname, Email, Username,
            Password, PasswordConfirm,Number, Birthdate;
    private TextView Programm;
    private String Birth;
    private ImageView EyePwd,EyePwdConfirm;
    DatePickerDialog picker;
    private ImageView PreviewImage;
    private CircleImageView circlePicture;
    private byte[] Picture;
    private String gend;
    private String SelectedService,SelectedType;
    private String [] services,employeTypes;
    private  Spinner spinnerServices , spinnerTypes;
    private int Start=8,End=17;
    final  String PREFS_NAME="MyPrefsFile";
    boolean editUsername,useMailAsUsername,generatePassword,notice;
     CheckBox monday,tuesday,wednesday,thursday,friday,satursday,sunday;
     //private Button workManager;

    CheckBox[] myTable=new CheckBox[7];
    byte[] days=new byte[7];




    @SuppressLint({"DefaultLocale", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);
       // Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

//workManager=findViewById(R.id.work_manager);
//workManager.setOnClickListener(this);
        initView();
        retrieveSharedPreferences();
        if(useMailAsUsername) {
            Username.setEnabled(false);
            //Username.addTextChangedListener(this);
            Email.addTextChangedListener(this);
        }
        if(generatePassword)
        {
            Password.setText(generatePassword());
            Password.setEnabled(false);
            PasswordConfirm.setText(generatePassword());
            PasswordConfirm.setEnabled(false);

        }


        gend="M";



            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.general_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    public void retrieveSharedPreferences(){

        SharedPreferences preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editUsername=preferences.getBoolean("editUsername",true);
       // generateUsername=preferences.getBoolean("generateUsername",false);
        useMailAsUsername=preferences.getBoolean("emailAsUsername",false);
        generatePassword=preferences.getBoolean("generatePassword",false);
        notice=preferences.getBoolean("notifyDuringAdd",true);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      /* *switch (item.getItemId())
        {
            case R.id.more:

                //
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void imageChooser() {
        Intent intent = new Intent();
        //to specify any kind of image
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(intent);
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
                        circlePicture.setImageBitmap(selectedImageBitmap);

                    }
                }

            });

    @SuppressLint("DefaultLocale")
        @Override
    public void onClick(View v) {
        String username,password;
       /* if(v.getId()==R.id.work_manager)
        {
            new ToastMessage(this,"kfgkl");
            Intent intent=new Intent(RegisterEmployee.this,MainActivity.class);
            startActivity(intent);
        }*/

  if(v.getId()==R.id.register_show_password)
      registerEmployeePresenter.onShowHidePassword(Password.getId(),v.getId());
  else if(v.getId()==R.id.register_show_password_confirm)
      registerEmployeePresenter.onShowHidePassword(PasswordConfirm.getId(),v.getId());

     else     if (v.getId() == R.id.register_picture_button)
            imageChooser();
        else if (v.getId() == R.id.register_button ) {
          days= workdays();
          username=toString(Username);
          /*if(useMailAsUsername)
                username=toString(Email);*/
            registerEmployeePresenter.onRegisterEmployee(toString(Number), toString(Lastname),
                    toString(Firstname),gend,Birth,toString(Email),username,
                    toString(Password),toString(PasswordConfirm),SelectedService,
                    Start,End,Picture,SelectedType,days );




        }
        else if (v.getId() == R.id.register_reset_button)

            resetInput();
        else if (v.getId() == R.id.register_birthdate) {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

 //while(toString())
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

    public String  generatePassword(){
        return "Aab10%";
    }
    public  void resetInput() {

        Number.setText("");
        Lastname.setText("");
        Firstname.setText("");
        Birthdate.setText("");
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
int pos;
        if(parent.getId()==R.id.register_service) {
            //SelectedService = String.valueOf(spinnerServices.getSelectedItem());
            SelectedService = parent.getItemAtPosition(position).toString();
            /*pos=position;
            SelectedService=services[pos];*/

        }
        else if(parent.getId()==R.id.register_type) {
            //SelectedType=String.valueOf(spinnerTypes.getSelectedItem());
            SelectedType = parent.getItemAtPosition(position).toString();
            /*pos=position;
            SelectedType=employeTypes[pos];*/

        }

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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    @Override
    public void onRegisterEmployeeSuccess() {
        String message=getString(R.string.registration_successful);

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        resetInput();
    }

    @Override
    public void onRegisterEmployeeError(String message) {
      Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowHidePassword(int passwordId, int eyeId) {
         ImageView eye=findViewById(eyeId);
         EditText pwd=findViewById(passwordId);
        if(pwd.getTransformationMethod().
                equals(PasswordTransformationMethod.getInstance()))
        {
            eye.setImageResource(R.drawable.ic_baseline_visibility_off_18);


            //show password
            pwd.setTransformationMethod(HideReturnsTransformationMethod.
                    getInstance());
        }
        else{
            eye.setImageResource(R.drawable.baseline_visibility_black_18);


            //hide password
            pwd.setTransformationMethod(PasswordTransformationMethod.
                    getInstance());
        }


    }

    @Override
    public void sendEmail(String[] to, String subject, String message, String qrCodeFileName) {

        File qrCodepicture =new File(getFilesDir(),qrCodeFileName);

        Uri qrCodeUri;


       qrCodeUri= FileProvider.getUriForFile(RegisterEmployee.this,
                getApplicationContext().getPackageName()+".provider", qrCodepicture);


        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);
        emailIntent.setType("image/png");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.putExtra(Intent.EXTRA_STREAM,qrCodeUri);
        if(notice)
            startActivity(Intent.createChooser(emailIntent, "Envoyer avec"));


    }

    public String toString(EditText e){
        return  e.getText().toString();

    }
    public void initView(){

        EyePwd=findViewById(R.id.register_show_password);
        EyePwdConfirm=findViewById(R.id.register_show_password_confirm);
        EyePwdConfirm.setOnClickListener(this);
        EyePwd.setOnClickListener(this);


        //new controller instance created
        registerEmployeePresenter = new RegisterEmployeeController(this);
        // The view gets service list from the controller
         services = registerEmployeePresenter.onLoad();
          employeTypes=getResources().getStringArray(R.array.employee_types);
        SelectedType=employeTypes[0];
        SelectedService=services[0];
        spinnerServices = findViewById(R.id.register_service);
         spinnerTypes = findViewById(R.id.register_type);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);

         dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeTypes);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(dataAdapterR);


        NumberPicker start = findViewById(R.id.register_planning_start_choose);
        NumberPicker end = findViewById(R.id.register_planning_end_choose);
        start.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        end.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        Programm=findViewById(R.id.prog);
        Programm.setText(programm());
        Number = findViewById(R.id.register_number);
        Lastname = findViewById(R.id.register_lastname);
        Firstname = findViewById(R.id.register_firstname);
        RadioGroup gender = findViewById(R.id.register_gender);
        Birthdate = findViewById(R.id.register_birthdate);
        Birthdate.setClickable(true);
        Birthdate.setFocusable(false);
        Birthdate.setInputType(InputType.TYPE_NULL);
        Email = findViewById(R.id.register_email);
        Username = findViewById(R.id.register_username);
        Password = findViewById(R.id.register_password);
        PasswordConfirm = findViewById(R.id.register_password_confirm);
        //PreviewImage = findViewById(R.id.register_preview_image);
        circlePicture=findViewById(R.id.register_preview_image);
        Button register = findViewById(R.id.register_button);
        Button reset = findViewById(R.id.register_reset_button);
        Button selectPicture = findViewById(R.id.register_picture_button);
        gender.setOnCheckedChangeListener(this);
        Birthdate.setOnClickListener(this);
        register.setOnClickListener(this);
        reset.setOnClickListener(this);
        selectPicture.setOnClickListener(this);

        spinnerServices.setOnItemSelectedListener(this);
        spinnerTypes.setOnItemSelectedListener(this);

        start.setFormatter(this);
        end.setFormatter(this);
        initNumberPicker(start,6,9);
        initNumberPicker(end,16,19);

                monday=findViewById(R.id.monday);
                tuesday=findViewById(R.id.tuesday);
                wednesday=findViewById(R.id.wednesday);
                thursday=findViewById(R.id.thursday);
                friday=findViewById(R.id.friday);
                satursday=findViewById(R.id.satursday);
                sunday=findViewById(R.id.sunday);
        myTable[0]=monday;
        myTable[1]=tuesday;
        myTable[2]=wednesday;
        myTable[3]=thursday;
        myTable[4]=friday;
        myTable[5]=satursday;
        myTable[6]=sunday;

        myTable[5].setChecked(false);
        myTable[6].setChecked(false);
    }

    public void initNumberPicker(NumberPicker n,int min,int max ){
                 if(n!=null) {
            n.setMinValue(min);
            n.setMaxValue(max);
            n.setWrapSelectorWheel(true);
            n.setOnValueChangedListener(this);

        }





    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

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

    @Override
    public String format(int value) {
        String str;
        str=String.valueOf(value);
        if(value<10)
            str="0"+value;
        return str;
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
    public byte[] workdays(){
        byte[] tab =new byte[7];
        int i;
       for(i=0;i<7;i++)
        {
            /*if(myTable[i].isChecked())
                tab[i]='T'; //for true
            else
                tab[i]='F';*/
            tab[i]= (byte) (myTable[i].isChecked()?'T':'F');


        }


return tab;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Username.setText(s.toString());

    }
}
