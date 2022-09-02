package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
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

    private ImageView image;
    private Integer Start, End;

    IDeleteEmployeeController deleteEmployeePresenter;
    private ToastMessage Toast;

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

        //delete button
        if (v.getId() == R.id.delete_button)

            deleteEmployeePresenter.onDeleteEmployee();



    }


    public void initView() throws ParseException {


        int actionNumber = getIntent().getIntExtra("ACTION_NUMBER", 1);

        Hashtable<String, Object> informations = new Hashtable<>();
        deleteEmployeePresenter.onLoad(actionNumber, informations);

        EditText number = findViewById(R.id.register_number);
        EditText lastname = findViewById(R.id.register_lastname);
        EditText email = findViewById(R.id.register_email);
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
        Toast = new ToastMessage(this, message);
        Toast.show();

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
                    startActivity(getIntent());
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