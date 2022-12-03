package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

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

        IDeleteEmployeeView {
    private Integer Start, End;

    IDeleteEmployeeController deleteEmployeePresenter;
    boolean notice;
    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveSharedPreferences();
        setContentView(R.layout.activity_delete_employee);

        deleteEmployeePresenter = new DeleteEmployeeController(this);

            initView();



    }
    public void  retrieveSharedPreferences(){
        String PREFS_NAME="MyPrefsFile";
        SharedPreferences preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        notice=preferences.getBoolean("notifyDelete",true);

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
    public void onClick(View v) {

        //delete button
        if (v.getId() == R.id.delete_button)

            deleteEmployeePresenter.onDeleteEmployee();



    }


    public void initView()  {


        int actionNumber = getIntent().getIntExtra("ACTION_NUMBER", 1);

        Hashtable<String, Object> informations = new Hashtable<>();
        try {
            deleteEmployeePresenter.onLoad(actionNumber, informations);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText number = findViewById(R.id.register_number);
        EditText lastname = findViewById(R.id.register_lastname);
         email = findViewById(R.id.register_email);
        EditText firstname = findViewById(R.id.register_firstname);
        EditText username = findViewById(R.id.register_username);
        EditText birthdate = findViewById(R.id.register_birthdate);
        EditText service = findViewById(R.id.register_service);
        EditText type = findViewById(R.id.register_type);

        ImageView previewImage = findViewById(R.id.register_preview_image);
        Button delete = findViewById(R.id.delete_button);
        RadioGroup gender = findViewById(R.id.register_gender);
        EditText programm = findViewById(R.id.prog);
        previewImage.setImageBitmap((Bitmap) informations.get("picture"));
        number.setText(Objects.requireNonNull(informations.get("number")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
        firstname.setText(Objects.requireNonNull(informations.get("firstname")).toString());
        lastname.setText(Objects.requireNonNull(informations.get("lastname")).toString());
        email.setText(Objects.requireNonNull(informations.get("email")).toString());
        username.setText(Objects.requireNonNull(informations.get("username")).toString());

        birthdate.setText(Objects.requireNonNull(informations.get("birthdate")).toString());

        if (Objects.requireNonNull(informations.get("birthdate")).toString().equals(""))
        {
            findViewById(R.id.tv).setVisibility(View.GONE);
            birthdate.setVisibility(View.GONE);

        }
        if (Objects.equals(informations.get("gender"), 'F'))
                  gender.setId(R.id.register_girl);


        service.setText((String) informations.get("service"));
               type.setText((String) informations.get("type"));

        if (informations.containsKey("start") && informations.containsKey("end")) {
            Start = (Integer) informations.get("start");
            End = (Integer) informations.get("end");
            programm.setText(programm());
        }


        //Not updatable
        number.setEnabled(false);
        firstname.setEnabled(false);
        lastname.setEnabled(false);
        birthdate.setEnabled(false);
        username.setEnabled(false);
        gender.setEnabled(false);
        programm.setEnabled(false);
        gender.getChildAt(0).setEnabled(false);
        gender.getChildAt(1).setEnabled(false);
        type.setEnabled(false);
        email.setEnabled(false);
        service.setEnabled(false);


        // Listeners
        delete.setOnClickListener(this);


    }

    @Override
    public void onDeleteSuccessfull(String message) {
        String subject="Notification de suppression d'employé";
        String msg="Vous avez été supprimé de la liste des employés";
         new ToastMessage(this, message);
         sendEmail(new String[]{email.getText().toString()},subject,msg);

    }

    @Override
    public void askConfirmDelete(String pos, String neg, String title, String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(pos, (dialog, which) -> {

                     new ToastMessage(DeleteEmployee.this,"Confirmé");
                    deleteEmployeePresenter.onConfirmResult(true);
                    DeleteEmployee.this.finish();
                    //startActivity(getIntent());
                })
                .setNegativeButton(neg, (dialog, which) -> {

                    new ToastMessage(DeleteEmployee.this,"Annulé");

                    deleteEmployeePresenter.onConfirmResult(false);
                    DeleteEmployee.this.finish();
                    startActivity(getIntent());
                });

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
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