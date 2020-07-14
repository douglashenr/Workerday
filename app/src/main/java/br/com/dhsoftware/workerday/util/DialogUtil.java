package br.com.dhsoftware.workerday.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.fragments.AlertDialogCustom;

public class DialogUtil {

    private Context context;
    private IntentUtil intentUtil;

    public DialogUtil(Context context) {
        intentUtil = new IntentUtil(context);
        this.context = context;
    }

    public void infoDialog(String text) {
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(context);
        builderAlertDialog.setMessage(text);
        builderAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }


    public void welcomeDialog() {
        AlertDialogCustom dialogCustom = new AlertDialogCustom(context);
        dialogCustom.showDialogCustomWelcome();
    }

    public void showToast(String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public void imageDialog(final ImageView imageView){
        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(context);
        builderAlertDialog.setTitle("Selecione uma ação:");
        builderAlertDialog.setItems(R.array.dialogImage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        showToast("Imagem deletada!", Toast.LENGTH_LONG);
                        imageView.setImageBitmap(null);
                        imageView.setTag("");
                        break;
                    case 1:
                        showToast("Exibir imagem: ", Toast.LENGTH_LONG);
                        intentUtil.startIntentOpenImage(imageView.getTag().toString());
                        break;

                    case 2:
                        showToast("Alterar imagem", Toast.LENGTH_LONG);
                        break;
                }
            }
        }).show();
    }


}
