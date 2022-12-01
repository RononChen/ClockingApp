package uac.imsp.clockingapp.View.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

import uac.imsp.clockingapp.R;

public class ManageUsername extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
CompoundButton.OnCheckedChangeListener{

    RadioButton emailAsUsername,generateUsername,generatePassword;
    RadioGroup radioGroup;
    SwitchMaterial editUsername,showPassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean EmailAsUsername,GenUsername,GenPwd, EditUsername, EditPassword,Add,Update,Delete;
    CheckBox add,update,delete;
    final  String PREFS_NAME="MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_username);
        initView();
        retrieveSharedPreferences();
        emailAsUsername.setChecked(EmailAsUsername);
        generateUsername.setChecked(GenUsername);
        generatePassword.setChecked(GenPwd);
        editUsername.setChecked(EditUsername);
        showPassword.setChecked(EditPassword);
        add.setChecked(Add);
        update.setChecked(Update);
        delete.setChecked(Delete);
    }
    public void initView(){
        emailAsUsername=findViewById(R.id.use_email);
        generateUsername=findViewById(R.id.generate_username);
        generatePassword=findViewById(R.id.generate_password);
        editUsername =findViewById(R.id.switch_edit_username);
        editUsername.setOnCheckedChangeListener(this);
        showPassword=findViewById(R.id.switch_show_password);
        radioGroup=findViewById(R.id.password_group);
        radioGroup.setOnCheckedChangeListener(this);
        add=findViewById(R.id.setting_add);
        update=findViewById(R.id.setting_update);
        delete=findViewById(R.id.setting_delete);
        add.setOnCheckedChangeListener(this);

    }
    public void retrieveSharedPreferences(){
        preferences= getApplicationContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor=preferences.edit();
        EmailAsUsername=preferences.getBoolean("emailAsUsername",false);
        GenUsername=preferences.getBoolean("generateUsername",false);
        GenPwd=preferences.getBoolean("generatePassword",false);
       EditUsername =preferences.getBoolean("editUsername",true);
        EditPassword =preferences.getBoolean("showPasswordDuringAdd",true);
       Add=preferences.getBoolean("notifyAdd",true);
        Delete=preferences.getBoolean("notifyDelete",false);
        Update=preferences.getBoolean("notifyUpdate",false);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.username_group)
        {
            if (checkedId == R.id.generate_username)
            {
                editor.putBoolean("generateUsername",true);
            }
        else     if (checkedId == R.id.use_email)
            {
                editor.putBoolean("generateUsername",false);
            }
        }
        else if(group.getId()==R.id.password_group)
        {
            editor.putBoolean("generatePassword", checkedId == R.id.generate_password);
        }
        editor.apply();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId()==R.id.switch_show_password)
            editor.putBoolean("showPasswordDuringAdd",isChecked);
        else if(buttonView.getId()==R.id.switch_edit_username)
            editor.putBoolean("editUsername",isChecked);
        else if(buttonView.getId()==R.id.setting_add)
            editor.putBoolean("notifyAdd",isChecked);
        else if(buttonView.getId()==R.id.setting_update)
            editor.putBoolean("notifyUpdate",isChecked);
        else if(buttonView.getId()==R.id.setting_delete)
            editor.putBoolean("notifyDelete",isChecked);

        editor.apply();
    }
}