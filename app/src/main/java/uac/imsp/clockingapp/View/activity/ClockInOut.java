package uac.imsp.clockingapp.View.activity;

import android.Manifest;
import android.content.Intent;
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

public class ClockInOut extends AppCompatActivity
        implements View.OnClickListener, IClockInOutView {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    //private CameraSource cameraSource;
    private  CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    IClockInOutController clockInOutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initViews();

        clockInOutPresenter = new ClockingInOutController(this);
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setOnClickListener(this);
    }



    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {

        super.onResume();
        onLoad("");

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentData));
        if (intentData.length() > 0) {
            startActivity(intent);
        }
    }


    @Override
    public void onLoad(String welcome) {


        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        // initialise detectors and sources

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE) //CHANGED
                .build();

        CameraSource.Builder c = new CameraSource.Builder(this, barcodeDetector);

        c.setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedPreviewSize(1920,1080)
                .setAutoFocusEnabled(true);

        /* cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();*/

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


    public void onClockingSuccessful(String message) {
        //Intent intent=new Intent(this,Menu.cl)
      Toast.makeText(this,message,Toast.LENGTH_LONG).show();
      //startActivity(intent);
    }



    public void onClockingError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }
}