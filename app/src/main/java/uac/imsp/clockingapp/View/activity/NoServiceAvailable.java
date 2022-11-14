package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.R;

public class NoServiceAvailable extends AppCompatActivity
implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_service_available);
    }
    public void initView(){
        TextView errorMesssage = findViewById(R.id.no_service_message);
        Button button = findViewById(R.id.no_service_add);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
if(v.getId()==R.id.no_service_add){
    //start add service activity
    int a=2;
}

    }
}