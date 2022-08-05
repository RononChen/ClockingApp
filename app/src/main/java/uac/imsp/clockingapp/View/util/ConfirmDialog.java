package uac.imsp.clockingapp.View.util;

import android.app.AlertDialog;
import android.content.Context;

public class ConfirmDialog  extends AlertDialog.Builder {

    public ConfirmDialog(Context context,String title,String message) {
        super(context);
        setTitle(title);
        setMessage(message);
        create();
        //setIcon();

    }


}
