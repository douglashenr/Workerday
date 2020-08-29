package br.com.dhsoftware.workerday.util;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import br.com.dhsoftware.workerday.util.condition.CheckCondition;

public class TimePickerUtil implements TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private CheckCondition condition;

    private void setTimeInEditTextFromTimePickerDialog(Activity activity, EditText editText, CheckCondition condition) {
        this.editText = editText;
        this.condition = condition;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        /*
        Verifica se  a hora e/ou minuto é menor que 10 para colocar o 0 da frente
        Para ficar no formato do SimpleDataFormat
         */
        String time = DateUtil.getInstanceDateUtil().formatCorrectStringTime(hourOfDay, minute);

        if(condition != null){
            if(condition.condition(time)){
                editText.setText(time);
            }
        }

    }
}
