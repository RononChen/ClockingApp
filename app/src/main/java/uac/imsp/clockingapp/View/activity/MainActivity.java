package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* WorkManager workManager;
        Button btnStartOneTimeRequest, btnStartPeriodicRequest;
        workManager = WorkManager.getInstance();
        btnStartOneTimeRequest = findViewById(R.id.btn_one_time);
        btnStartPeriodicRequest = findViewById(R.id.btn_periodic_time);
        btnStartOneTimeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiresCharging(false)
                        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                        .build();

                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder
                        (NotiWorker.class)
                        .setConstraints(constraints)
                        .build();
                workManager.enqueue(oneTimeWorkRequest);

            }
        });

        btnStartPeriodicRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiresCharging(false)
                        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                        .setRequiresBatteryNotLow(false)
                        .setRequiresStorageNotLow(false)
                        .build();

                PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                        NotiWorker.class, 5, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .build();
                workManager.enqueue(periodicWorkRequest);
            }
        });*/
    }
}