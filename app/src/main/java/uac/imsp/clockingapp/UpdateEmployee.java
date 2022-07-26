package uac.imsp.clockingapp;

import static uac.imsp.clockingapp.Controller.RegisterEmployeeController.getBytesFromBitmap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import uac.imsp.clockingapp.Controller.IUpdateEmployeeController;
import uac.imsp.clockingapp.Controller.UpdateEmployeeController;
import uac.imsp.clockingapp.View.IUpdateEmployeeView;

public class UpdateEmployee extends AppCompatActivity
                           implements View.OnClickListener,
                           AdapterView.OnItemSelectedListener,
        IUpdateEmployeeView {
private Button Update;
private String [] Numbers, Services;
private String SelectedNumber,SelectedService,selectedService,startTime,endTime;
private  Spinner spinnerNumbers,spinnerServices;
private ImageView image;
private  byte[] picture;

IUpdateEmployeeController updateEmployeeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        updateEmployeeController=new UpdateEmployeeController(this);
        initView();
       // updateEmployeeController.


    }

    @Override
    public void onClick(View v) {
     //bouton modifier
  //if(v.getId()==R.id.update_button)

     updateEmployeeController.onUpdateEmployee(selectedService,toString(),null,picture);
  /*else if(v.getId()==R.id.reset_button)
      updateEmployeeController.onReset();*/


        //else if(v.getId()==R.id.update_picture)
            imageChooser();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // if(view.getId()==R.id.update_number_list) {
            //Recuperer le matricule sélectionné
            SelectedNumber = String.valueOf(spinnerNumbers.getSelectedItem());
            //Le passer au controller
            updateEmployeeController.onNumberSelected(Integer.parseInt(SelectedNumber));
        }
        //else if(view.getId()==R.id.update_service_list)
            //recuperer le service sélectionné
           // SelectedService=String.valueOf(spinnerServices.getSelectedItem());

    //}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void initView(){
        //Pour récupérer les listes de matricules et de services
        Numbers=updateEmployeeController.onLoad(Services);
        //spinnerNumbers=findViewById(R.id.update_number_list);
        //spinnerServices=findViewById(R.id.update_service_list);
       // Update=findViewById(R.id.update_button);
       // image=findViewById(R.id.update_picture);
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Numbers);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumbers.setAdapter(dataAdapterR);
        dataAdapterR=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Services);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(dataAdapterR);
        spinnerServices.setOnItemSelectedListener(this);
        Update.setOnClickListener(this);
        image.setOnClickListener(this);

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
                            picture = getBytesFromBitmap(selectedImageBitmap);
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
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingChanged(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReset() {
        //Redémarrer l'activité
        Intent starterIntent=getIntent();
        startActivity(starterIntent);
      //onRestart();
    }
}