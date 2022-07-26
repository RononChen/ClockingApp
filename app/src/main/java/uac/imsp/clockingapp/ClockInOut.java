package uac.imsp.clockingapp;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import uac.imsp.clockingapp.Models.ClockingManager;
import uac.imsp.clockingapp.Models.Employee;

public class ClockInOut extends AppCompatActivity implements View.OnClickListener {


    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    private Employee employee;
    private ClockingManager datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        datasource=new ClockingManager(this);
        datasource.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initViews();



    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setOnClickListener(this);
    }


    private void initialiseDetectorsAndSources() {
         String msg="Scannez votre code QR pour pointer";

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();
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
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {
                            int number=0;
                            btnAction.setText("LAUNCH URL");
                            intentData = barcodes.valueAt(0).displayValue;
                            txtBarcodeValue.setText(intentData);
                            number=Integer.parseInt(txtBarcodeValue.getText().toString());
                            employee=new Employee(number);
                            clock();
                            datasource.close();
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentData));
        if (intentData.length() > 0) {
            startActivity(intent);
        }
    }
    public boolean clock(){
        String message="";
        if (datasource.hasNotClockedIn(employee)) {

            datasource.clockIn(employee);
            message="Entrée marquée avec succès";
        }
        else if (datasource.hasNotClockedOut(employee)) {
            datasource.clockOut(employee);
            message="Sortie marquée avec succès";
        }
        else {
            message="Vous avez déjà marqué votre entrée-sortie";
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        return  true;
    }
}