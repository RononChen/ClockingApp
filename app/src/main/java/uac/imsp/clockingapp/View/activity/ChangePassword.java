package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.ChangePasswordController;
import uac.imsp.clockingapp.Controller.util.IChangePasswordController;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IChangePasswordView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class ChangePassword extends AppCompatActivity
        implements IChangePasswordView, View.OnClickListener {
    private TextView username;
    private EditText oldPassword,newPassword,newPasswordConfirm;
    private IChangePasswordController changePasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();

        changePasswordPresenter=new ChangePasswordController(this) ;
        changePasswordPresenter.onStart(1);

    }

    @Override
    public void onSuccess(String message) {
        new ToastMessage(this,message);

    }

    @Override
    public void onWrongPassword(String message) {
        new ToastMessage(this,message);

    }

    @Override
    public void onStart(String username) {
        this.username.setText(username);

    }
    public void initView(){
        username=findViewById(R.id.change_password_username);
        oldPassword=findViewById(R.id.change_password_old_password);
        newPassword=findViewById(R.id.change_password_new_password);
        newPasswordConfirm =findViewById(R.id.change_password_new_password_confirm);
        Button updatePassword = findViewById(R.id.change_password_button);
        updatePassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        changePasswordPresenter.onSubmit(oldPassword.getText().toString().trim(),
                newPassword.getText().toString().trim(),
                newPasswordConfirm.getText().toString().trim()
        );

    }
}
