package uac.imsp.clockingapp.View.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import uac.imsp.clockingapp.Controller.control.ClockingInOutController;
import uac.imsp.clockingapp.Controller.util.IClockInOutController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IClockInOutView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class ClockInOut extends AppCompatActivity
        implements View.OnClickListener, IClockInOutView {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private  CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    IClockInOutController clockInOutPresenter;
    boolean useQRcode;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveSharedPreferences();
        setContentView(useQRcode? R.layout.activity_scanner:R.layout.fingerprint_gesture);
        initViews();

        clockInOutPresenter = new ClockingInOutController(this);
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setOnClickListener(this);
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
        onLoad();

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

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE) //CHANGED
                .build();

        CameraSource.Builder c = new CameraSource.Builder(this, barcodeDetector);

        c.setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedPreviewSize(1920,1080)
                .setAutoFocusEnabled(true);

        cameraSource= c.build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ClockInOut.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ClockInOut.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

             @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                AtomicInteger number= new AtomicInteger();
                if (barcodes.size() != 0) {
                    //@Override
                    txtBarcodeValue.post(() -> {

                        btnAction.setText(R.string.LAUNCH_URL);
                        intentData = barcodes.valueAt(0).displayValue;
                        txtBarcodeValue.setText(intentData);
                        try{
                        number.set(Integer.parseInt(txtBarcodeValue.getText().toString()));
                            //Start clocking
                            clockInOutPresenter.onClocking(number.get());

                        }
                        catch (NullPointerException e){
                            e.printStackTrace();
                        }


                    });
                }
            }
        });


    }


    public void onClockInSuccessful() {
      new ToastMessage(this,getString(R.string.enter_pointed));

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
}