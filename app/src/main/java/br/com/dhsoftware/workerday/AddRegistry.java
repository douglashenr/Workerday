package br.com.dhsoftware.workerday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.enumObservation;
import br.com.dhsoftware.workerday.util.DateUtil;

public class AddRegistry extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener, RadioGroup.OnCheckedChangeListener {

    EditText dateWorked, entranceTime, entranceLunchTime, leaveLunchTime, leaveTime, requiredTime, percentExtraWork;
    EditText editTextSetDataOrTimeFromPicker;
    RadioGroup buttonGroup;
    RadioButton radioButtonAtestado, radioButtonDeclaration;
    ImageButton saveButton;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_registry);
        setView();

        user = new User("Douglas", "douglas@hotmail.com");

    }


    public void setView(){
        dateWorked = findViewById(R.id.date_worked);
        dateWorked.setOnClickListener(this);

        entranceTime = findViewById(R.id.entrance_time);
        entranceTime.setOnClickListener(this);

        entranceLunchTime = findViewById(R.id.entrance_lunch_time);
        entranceLunchTime.setOnClickListener(this);

        leaveLunchTime = findViewById(R.id.leave_lunch_time);
        leaveLunchTime.setOnClickListener(this);

        leaveTime = findViewById(R.id.leave_time);
        leaveTime.setOnClickListener(this);

        requiredTime = findViewById(R.id.required_time_to_work_time);
        requiredTime.setOnClickListener(this);

        percentExtraWork = findViewById(R.id.porcent_extra_work);
        percentExtraWork.setOnClickListener(this);

        buttonGroup = findViewById(R.id.button_group);
        buttonGroup.setOnCheckedChangeListener(this);

        radioButtonAtestado = findViewById(R.id.button_atestado);
        radioButtonDeclaration = findViewById(R.id.button_time_declaration);

        saveButton = findViewById(R.id.add_save_registry);
        saveButton.setOnClickListener(this);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ) {
        editTextSetDataOrTimeFromPicker.setText(dayOfMonth + "/" + month + "/" + year);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        editTextSetDataOrTimeFromPicker.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonAtestado.getId())))
            enableEditText(false);

        else
            enableEditText(true);

        System.out.println(group.getCheckedRadioButtonId() +"----"+ radioButtonAtestado.getId());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.date_worked:
                setEditTextSetDataOrTimeFromPicker(dateWorked);
                datePickerDialog();
                break;

            case R.id.entrance_time:
                setEditTextSetDataOrTimeFromPicker(entranceTime);
                timePickerDialog();
                break;

            case R.id.entrance_lunch_time:
                setEditTextSetDataOrTimeFromPicker(entranceLunchTime);
                timePickerDialog();
                break;
            case R.id.leave_lunch_time:
                setEditTextSetDataOrTimeFromPicker(leaveLunchTime);
                timePickerDialog();
                break;
            case R.id.leave_time:
                setEditTextSetDataOrTimeFromPicker(leaveTime);
                timePickerDialog();
                break;
            case R.id.required_time_to_work_time:
                timePickerDialog();
                setEditTextSetDataOrTimeFromPicker(requiredTime);
                break;
            case R.id.add_save_registry:
                validator();
                break;
        }


    }

    private void timePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this,  calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void datePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        //calendar.setTime(date);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.DAY_OF_MONTH);
        datePickerDialog.show();
    }


    public void enableEditText(boolean active){
        entranceTime.setEnabled(active);
        entranceLunchTime.setEnabled(active);
        leaveTime.setEnabled(active);
        leaveLunchTime.setEnabled(active);
        requiredTime.setEnabled(active);
        percentExtraWork.setEnabled(active);
    }



    public void validator(){
        if(radioButtonAtestado.isChecked()){
            if(dateWorked.getText().toString().equals("")){
                System.out.println("Não salvou porque DataWorked está vazio");
                dateWorked.setTextColor(Color.RED);
                entranceTime.setHintTextColor(Color.GRAY);
            }else {
                System.out.println("Salvou com atestado");


                createObjectRegistry();
            }
        }else{
            if(dateWorked.getText().toString().equals("") && entranceTime.getText().toString().equals("")){
                System.out.println("Não salvou porque DataWorked e entraceTime está vazio");
                dateWorked.setHintTextColor(Color.RED);
                entranceTime.setHintTextColor(Color.RED);
            }else if(entranceTime.getText().toString().equals("")){
                entranceTime.setHintTextColor(Color.RED);
                System.out.println("Não salvou porque entraceTime está vazio");
            }else if (dateWorked.getText().toString().equals("")){
                dateWorked.setHintTextColor(Color.RED);
                System.out.println("Não salvou porque DateWorked está vazio");
            }else{
                System.out.println("Salvou" + dateWorked.getText());
            }
        }
    }

    private void createObjectRegistry() {
        Registry registry = new Registry(user, DateUtil.getInstanceDateUtil().convertStringDataToCalendar(dateWorked.getText().toString()), enumObservation.ATESTADO);
        System.out.println(registry);
    }


    public EditText getEditTextSetDataOrTimeFromPicker() {
        return editTextSetDataOrTimeFromPicker;
    }

    public void setEditTextSetDataOrTimeFromPicker(EditText editTextSetDataOrTimeFromPicker) {
        this.editTextSetDataOrTimeFromPicker = editTextSetDataOrTimeFromPicker;
    }


}
