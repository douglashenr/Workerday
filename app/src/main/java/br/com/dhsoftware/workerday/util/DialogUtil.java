package br.com.dhsoftware.workerday.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.fragments.AlertDialogCustom;
import br.com.dhsoftware.workerday.util.textWatcher.MoneyTextWatcher;

public class DialogUtil{

    private Context context;
    private AlertDialog dialog;

    public DialogUtil(Context context) {
        this.context = context;

    }

    public void infoDialog(String text){
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(context);
        builderAlertDialog.setMessage(text);
        builderAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }


    public void welcomeDialog(){
        AlertDialogCustom dialogCustom = new AlertDialogCustom(context);
        dialogCustom.showDialogCustomWelcome();
    }

    public void showToast(String text, int duration){
        Toast.makeText(context, text, duration).show();
    }


}
