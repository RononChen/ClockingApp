package uac.imsp.clockingapp.View.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

import uac.imsp.clockingapp.Controller.control.ClockingInOutController;
import uac.imsp.clockingapp.Controller.util.IClockInOutController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IClockInOutView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class ClockInOut extends AppCompatActivity
        implements View.OnClickListener, IClockInOutView {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    int Number;
    BarcodeDetector barcodeDetector;
    private  CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    //Button btnAction;
    String intentData = "";
    IClockInOutController clockInOutPresenter;
    boolean useQRcode;
    SharedPreferences preferences;
   boolean firstRun=true;

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.switch_camera_orientation) {

            clockInOutPresenter.onSwitchCamara();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
// showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.clocking_text);
        retrieveSharedPreferences();
        setContentView(useQRcode? R.layout.activity_scanner:R.layout.fingerprint_gesture);
        initViews();

        clockInOutPresenter = new ClockingInOutController(this);
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);

    }
public void retrieveSharedPreferences(){
        String PREFS_NAME="MyPrefsFile";
    preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
            Context.MODE_PRIVATE);
    useQRcode=preferences.getBoolean("useQRCode",true);
}


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {

        super.onResume();

        //onLoad();

    }
    public void front(){
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();
    }

    public void back(){
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();
    }


    private void switchCamera(){
        if(firstRun) {
            back();
            startCamera();
            firstRun=false;
            return;

        }
        cameraSource.stop();
        cameraSource.release();

            if (Objects.requireNonNull(cameraSource).getCameraFacing() == CameraSource.CAMERA_FACING_FRONT) {
               back();
            } else {
                front();
            }
           startCamera();

    }


    public boolean onCreateOptionsMenu(@NonNull android.view.Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.camera_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    void startCamera(){
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            cameraSource.start(surfaceView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        if (intentData.length() > 0) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
        }
    }


    @Override
    public void onLoad() {
       new ToastMessage(this, getString(R.string.clocking_text));
        // initialise detectors and sources

         barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>()
        {
            @Override
            public void release() {
               new  ToastMessage(getApplicationContext(),
                        "To prevent memory leaks barcode scanner has been stopped");

            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {


                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    //@Override
                    txtBarcodeValue.post(() -> {


                        intentData = barcodes.valueAt(0).displayValue;
                        txtBarcodeValue.setText(intentData);
                        try {
                            Number = Integer.parseInt(txtBarcodeValue.getText().toString());

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            Number = 0;
                        }
                        clockInOutPresenter.onClocking(Number);

                    });
                }



                }
            });

        switchCamera();
    }

    public void onClockInSuccessful() {
      new ToastMessage(getApplicationContext(),getString(R.string.enter_pointed));

    }

    @Override
    public void onClockOutSuccessful() {
        new ToastMessage(this,getString(R.string.out_pointed));
    }


    public void onClockingError(int errorNumber) {
        switch (errorNumber){
            case 1:
               new ToastMessage(this,getString(R.string.employee_not_found));
               break;
            case 2:
                new ToastMessage(this,getString(R.string.should_not_work_today));
                break;
            case 3:

                new ToastMessage(this,getString(R.string.in_out_already_marked));
                break;
            default:
                break;
        }


    }

    @Override
    public void onSwitchCamera() {
        if(firstRun)
        switchCamera();
        firstRun = false;

    }

}