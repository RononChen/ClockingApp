package uac.imsp.clockingapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import uac.imsp.clockingapp.View.util.IUpdateEmployeeView;

@RunWith(AndroidJUnit4.class)
public class UpdateEmployeeTest  implements IUpdateEmployeeView {

    @Override
    public void onSomethingchanged(String message) {

    }

    @Override
    public void onNothingChanged(String message) {

    }

    @Override
    public void onUpdateEmployeeError(String message) {

    }

    @Override
    public void askConfirmUpdate(String pos, String neg, String title, String message) {

    }
}
