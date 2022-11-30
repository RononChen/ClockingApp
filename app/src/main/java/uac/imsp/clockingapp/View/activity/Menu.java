package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uac.imsp.clockingapp.Controller.control.MenuController;
import uac.imsp.clockingapp.LocalHelper;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.IMenuView;

public class Menu extends AppCompatActivity  implements View.OnClickListener,
        IMenuView, DialogInterface.OnClickListener {


    private TextView language;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    MenuController menuPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        initView();
        menuPresenter=new MenuController(this);
    }
    public void initView(){
        language=findViewById(R.id.menu_language);
        language.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.menu_language)
              onLanguageMenu();

    }

    @Override
    public void onLanguageMenu() {
        String  [] options=getResources().getStringArray(R.array.languages);
        builder=new AlertDialog.Builder(this);
        builder.setItems(options,this);
        dialog=builder.create();
        dialog.show();

    }

    @Override
    public void changeLanguageTo(String lang) {
         LocalHelper.setLocale(Menu.this,lang);


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        menuPresenter.onLanguageSelected(which);

    }
}
