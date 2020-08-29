package br.com.dhsoftware.workerday.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.util.condition.CheckCondition;

public class DatePickerUtil implements DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private CheckCondition condition;
    private Activity activity;


    public void datePickerDialog(Activity activity, EditText editText, CheckCondition condition) {
        this.condition = condition;
        this.editText = editText;
        this.activity = activity;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(activity), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                /*month++ serve para arrumar o bug que retorna sempre o numero do mês anterior
        Também verifico se o mês ou o dia é menor que 10 para adicionar um 0 na frente, pois é necessario para o
        SimpleFormatData
         */
        month++;
        String date = DateUtil.getInstanceDateUtil().formatCorrectStringDate(dayOfMonth, month, year);


        //Irá verificar se a Data já existe no banco de dados, se existir não irá adicionar
        if(condition != null) {
            if (condition.condition(date))
                editText.setText(date);
            else
                new DialogUtil(activity.getApplicationContext()).showToast(activity.getString(R.string.date_already_registred), Toast.LENGTH_LONG);
        }else{
            editText.setText(date);
        }
    }

}
