package br.com.dhsoftware.workerday.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.textWatcher.MoneyTextWatcher;

public class AlertDialogCustom implements View.OnClickListener {
    private AlertDialog dialog;
    private Context context;
    private EditText salary, percentExtraSalary, deduction;

    public AlertDialogCustom(Context context) {
        this.context = context;
    }

    public void showDialogCustomWelcome() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_welcome, null, false);

        setLayoutView(view);

        //editText.addTextChangedListener(new PercentEditTextWatcher(editText));

        alertDialogBuilder.setView(view);

        dialog = alertDialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void setLayoutView(View view) {
        salary = view.findViewById(R.id.salary_welcome);
        salary.addTextChangedListener(new MoneyTextWatcher(salary));

        deduction = view.findViewById(R.id.deduction_welcome);
        deduction.addTextChangedListener(new MoneyTextWatcher(deduction));

        percentExtraSalary = view.findViewById(R.id.salary_percent_welcome);

        TextView textView = view.findViewById(R.id.fragment_welcome_textview_save);
        textView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (R.id.fragment_welcome_textview_save == v.getId()) {
            DialogUtil dialogUtil = new DialogUtil(context);
            saveDataToJson();
            dialogUtil.showToast("Informações iniciais adicionadas!", Toast.LENGTH_LONG);
            dialog.cancel();
        }
    }

    private void saveDataToJson() {
        JSONUser jsonUser = new JSONUser(context);

        if (!salary.getText().toString().equals("")) {
            jsonUser.setSalaryJSON(salary.getText().toString());
        }

        if (!deduction.getText().toString().equals("")) {
            jsonUser.setDeductionJSON(deduction.getText().toString());
        }

        if (!percentExtraSalary.getText().toString().equals("")) {
            jsonUser.setPercentExtraSalaryJSON(percentExtraSalary.getText().toString());
        }
    }
}
