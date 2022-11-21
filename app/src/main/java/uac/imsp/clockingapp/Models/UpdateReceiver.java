package uac.imsp.clockingapp.Models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.Models.entity.Employee;

public class UpdateReceiver extends BroadcastReceiver {
    private static final String TAG = BroadcastReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "onReceive: Running recurring task");
        Log.i(TAG,"BroadcastReceiver::OnReceive()");
        EmployeeManager employeeManager=new EmployeeManager(context.getApplicationContext());
        employeeManager.open();
        Employee [] employees=employeeManager.search("*");

        //Toast.makeText(context, "Your Alarm is there", Toast.LENGTH_LONG).show();

        //throw new UnsupportedOperationException("Not yet implemented");

    }
}