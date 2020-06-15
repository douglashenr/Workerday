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

public class DialogUtil implements View.OnClickListener {

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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.fragment_welcome, null, false);

        EditText editText = view.findViewById(R.id.salary_welcome);
        TextView textView = view.findViewById(R.id.fragment_welcome_textview_save);
        textView.setOnClickListener(this);

        //editText.addTextChangedListener(new PercentEditTextWatcher(editText));

        alertDialogBuilder.setView(view);

        dialog = alertDialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    public void showToast(String text, int duration){
        Toast.makeText(context, text, duration).show();
    }

    @Override
    public void onClick(View v) {
        if(R.id.fragment_welcome_textview_save == v.getId()){
            showToast("Informações iniciais adicionadas!", Toast.LENGTH_LONG);
            dialog.cancel();
        }
    }
}
