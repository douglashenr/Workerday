package br.com.dhsoftware.workerday.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import br.com.dhsoftware.workerday.FragmentController;
import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.enumObservation;



public class AddRegistryFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener, RadioGroup.OnCheckedChangeListener
        , Serializable {
    private View view;
    private EditText editTextDateWorked, editTextEntranceTime, editTextEntranceLunchTime, editTextLeaveLunchTime, editTextLeaveTime, editTextRequiredTime, editTextPercentExtraWork;
    private EditText editTextSetDataOrTimeFromPicker;
    private RadioGroup buttonGroup;
    private RadioButton radioButtonAtestado, radioButtonDeclaration, radioButtonNothing, radioButtonAbsence;
    private enumObservation observation;
    private Registry registryBundle;
    private FragmentController fragmentController;

    private DialogUtil dialogUtil;

    public AddRegistryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_registry, container, false);
        observation = enumObservation.NORMAL;
        fragmentController = new FragmentController(getFragmentManager());
        registryBundle = null;
        Bundle bundle = null;
        bundle = getArguments();
        try {
            registryBundle = (Registry) bundle.getSerializable("registry");
            System.out.println("id pego" + registryBundle.getId());
        } catch (Exception e) {
            e.getMessage();
        }
        setView();

        if (registryBundle != null) {
            setRegistryFromBundle();
        }

        dialogUtil = new DialogUtil(getActivity());

        return view;
    }

    private void setRegistryFromBundle() {
        editTextDateWorked.setText(registryBundle.getDayString());
        editTextEntranceTime.setText(registryBundle.getEntranceString());
        editTextEntranceLunchTime.setText(registryBundle.getEntranceLunchString());
        editTextLeaveLunchTime.setText(registryBundle.getLeaveLunchString());
        editTextLeaveTime.setText(registryBundle.getLeaveString());
        editTextRequiredTime.setText(registryBundle.getRequiredTimeToWorkString());
        editTextPercentExtraWork.setText(String.valueOf(registryBundle.getPercent()));

        //System.out.println("getObservation: " + registryBundle.getObservationString());

        if (registryBundle.getObservationString().equals(enumObservation.NORMAL.toString())) {
            radioButtonNothing.setChecked(true);
        }
        if (registryBundle.getObservationString().equals(enumObservation.ATESTADO.toString())) {
            radioButtonAtestado.setChecked(true);
        }
        if (registryBundle.getObservationString().equals(enumObservation.DECLARACAO_DE_HORAS.toString())) {
            radioButtonDeclaration.setChecked(true);
        }
        if (registryBundle.getObservationString().equals(enumObservation.ABSENCE.toString())) {
            radioButtonAbsence.setChecked(true);
        }

    }

    private void setView() {
        editTextDateWorked = view.findViewById(R.id.editText_dateWorked_registry);
        editTextDateWorked.setOnClickListener(this);

        editTextEntranceTime = view.findViewById(R.id.editText_entranceTime_registry);
        editTextEntranceTime.setOnClickListener(this);

        editTextEntranceLunchTime = view.findViewById(R.id.editText_entranceLunchTime_registry);
        editTextEntranceLunchTime.setOnClickListener(this);

        editTextLeaveLunchTime = view.findViewById(R.id.editText_leaveLunchTime_registry);
        editTextLeaveLunchTime.setOnClickListener(this);

        editTextLeaveTime = view.findViewById(R.id.editText_leaveTime_registry);
        editTextLeaveTime.setOnClickListener(this);

        editTextRequiredTime = view.findViewById(R.id.editText_requiredTimeToWorkTime_registry);
        editTextRequiredTime.setOnClickListener(this);

        editTextPercentExtraWork = view.findViewById(R.id.editText_porcentExtraWork_registry);
        editTextPercentExtraWork.setOnClickListener(this);

        buttonGroup = view.findViewById(R.id.button_group_registry);
        buttonGroup.setOnCheckedChangeListener(this);

        radioButtonAtestado = view.findViewById(R.id.button_atestado_registry);
        radioButtonDeclaration = view.findViewById(R.id.button_timeDeclaration_registry);
        radioButtonNothing = view.findViewById(R.id.button_nothing_registry);
        radioButtonAbsence = view.findViewById(R.id.button_absence_registry);

        ImageButton saveButton = view.findViewById(R.id.button_save_registry);
        saveButton.setOnClickListener(this);

    }

    private void completeAutomaticPercentExtraSalaryAndRequiredTimeField() {
        JSONUser jsonUser = new JSONUser(getActivity());
        if (!jsonUser.getPercentExtraSalary().equals(""))
            editTextPercentExtraWork.setText(jsonUser.getPercentExtraSalary());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //arrumar bug que ao selecionar o mes, retorna sempre o valor do mes com -1 do que deveria
        month++;
        String monthCustom = String.valueOf(month);
        String day = String.valueOf(dayOfMonth);
        if (month < 10) {
            monthCustom = "0" + monthCustom;
        }
        if (dayOfMonth < 10)
            day = "0" + day;
        String date = day + "/" + monthCustom + "/" + year;

        Dao dao = new Dao(getActivity());

        if (dao.isDateSet(date)) {
            dialogUtil.infoDialog("Data já cadastrada");
        } else {
            editTextSetDataOrTimeFromPicker.setText(date);
        }


    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hour;
        String minutes;

        if (hourOfDay < 10) {
            hour = "0" + hourOfDay;
        } else {
            hour = String.valueOf(hourOfDay);
        }

        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = String.valueOf(minute);
        }


        String time = hour + ":" + minutes;

        editTextSetDataOrTimeFromPicker.setText(validatorEditTextTime(time));
    }

    private String checkEntranceEditText(String time) {
        Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextLeaveTime.getText().toString().equals("")) {
            Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return "Horario de entrada não pode ser menor que o de saida";
            }
        }
        if (!editTextEntranceLunchTime.getText().toString().equals("")) {
            Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString());
            if (calendarEntranceLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return "Horario de entrada não pode ser menor que o de entrada para almoço";
            }
        } else if (!editTextLeaveLunchTime.getText().toString().equals("")) {
            Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return "Horario de entrada não pode ser menor que o de entrada para almoço";
            }
        }

        return "";

    }

    private String checkLeaveEditText(String time) {
        Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextEntranceTime.getText().toString().equals("")) {
            Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return "Horario de entrada não pode ser menor que o de saida";
            }
        }
        if (!editTextEntranceLunchTime.getText().toString().equals("")) {
            Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString());
            if (calendarEntranceLunch.getTime().getTime() > calendarLeave.getTime().getTime()) {
                return "Horario de entrada não pode ser menor que o de entrada para almoço";
            }
        }
        if (!editTextLeaveLunchTime.getText().toString().equals("")) {
            Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() > calendarLeave.getTime().getTime()) {
                return "Horario de entrada não pode ser menor que o de entrada para almoço";
            }
        }

        return "";
    }

    private String checkEntranceLunchEditText(String time) {
        Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextLeaveTime.getText().toString().equals("")) {
            Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarEntranceLunch.getTime().getTime()) {
                return "Horario de almoço não pode ser maior que o de saida";
            }
        }
        if (!editTextEntranceTime.getText().toString().equals("")) {
            Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceTime.getText().toString());
            if (calendarEntranceLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return "Horario de entrada do almoço não pode ser menor que o de entrada";
            }
        }
        if (!editTextLeaveLunchTime.getText().toString().equals("")) {
            Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntranceLunch.getTime().getTime()) {
                return "Horario de entrada do almoço não pode ser maior do que entrada para almoço";
            }
        }

        return "";
    }

    private String checkLeaveLunchEditText(String time) {
        Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextLeaveTime.getText().toString().equals("")) {
            Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarLeaveLunch.getTime().getTime()) {
                return "Horario de almoço não pode ser maior que o de saida";
            }
        }
        if (!editTextEntranceTime.getText().toString().equals("")) {
            Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return "Horario de entrada do almoço não pode ser menor que o de entrada";
            }
        }
        if (!editTextEntranceLunchTime.getText().toString().equals("")) {
            Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntranceLunch.getTime().getTime()) {
                return "Horario de entrada do almoço não pode ser maior do que entrada para almoço";
            }
        }

        return "";
    }

    private String validatorEditTextTime(String time) {
        if (editTextEntranceTime == editTextSetDataOrTimeFromPicker) {
            if (!checkEntranceEditText(time).equals("")) {
                dialogUtil.infoDialog(checkEntranceEditText(time));
                return "";
            } else {
                return time;
            }
        } else if (editTextEntranceLunchTime == editTextSetDataOrTimeFromPicker) {
            if (!checkEntranceLunchEditText(time).equals("")) {
                dialogUtil.infoDialog(checkEntranceLunchEditText(time));
                return "";
            } else {
                return time;
            }
        } else if (editTextLeaveLunchTime == editTextSetDataOrTimeFromPicker) {
            if (!checkLeaveLunchEditText(time).equals("")) {
                dialogUtil.infoDialog(checkLeaveLunchEditText(time));
                return "";
            } else {
                return time;
            }
        } else if (editTextLeaveTime == editTextSetDataOrTimeFromPicker) {
            if (!checkLeaveEditText(time).equals("")) {
                dialogUtil.infoDialog(checkLeaveEditText(time));
                return "";
            } else {
                return time;
            }
        }

        return time;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonAtestado.getId()))) {
            enableEditText(false);
            observation = enumObservation.ATESTADO;
        } else
            enableEditText(true);

        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonDeclaration.getId()))) {
            observation = enumObservation.DECLARACAO_DE_HORAS;
        }

        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonAbsence.getId()))) {
            observation = enumObservation.ABSENCE;
            enableEditText(false);
        }

        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonNothing.getId()))) {
            observation = enumObservation.NORMAL;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        completeAutomaticPercentExtraSalaryAndRequiredTimeField();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.editText_dateWorked_registry:
                setEditTextSetDataOrTimeFromPicker(editTextDateWorked);
                datePickerDialog();
                break;

            case R.id.editText_entranceTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextEntranceTime);
                timePickerDialog();
                break;

            case R.id.editText_entranceLunchTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextEntranceLunchTime);
                timePickerDialog();
                break;
            case R.id.editText_leaveLunchTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextLeaveLunchTime);
                timePickerDialog();
                break;
            case R.id.editText_leaveTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextLeaveTime);
                timePickerDialog();
                break;
            case R.id.editText_requiredTimeToWorkTime_registry:
                timePickerDialog();
                setEditTextSetDataOrTimeFromPicker(editTextRequiredTime);
                break;
            case R.id.button_save_registry:
                validator();
                break;
        }
    }

    private void timePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void datePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void enableEditText(boolean active) {
        editTextEntranceTime.setEnabled(active);
        editTextEntranceLunchTime.setEnabled(active);
        editTextLeaveTime.setEnabled(active);
        editTextLeaveLunchTime.setEnabled(active);
        editTextRequiredTime.setEnabled(active);
        editTextPercentExtraWork.setEnabled(active);
    }


    private void validator() {
        if (editTextDateWorked.getText().toString().equals("")) {
            System.out.println("Não salvou porque DataWorked está vazio");
            editTextDateWorked.setHintTextColor(Color.RED);
            dialogUtil.infoDialog("Complete o campo em vermelho para salvar!");
        } else {
            System.out.println("Salvou!");
            dialogUtil.showToast("Ponto salvo!", Toast.LENGTH_SHORT);
            createObjectRegistry();
            fragmentController.goBackFragment();
        }
    }

    private void createObjectRegistry() {
        Registry registry = new Registry();
        DateUtil dateUtil = new DateUtil();

        if (radioButtonAbsence.isChecked())
            registry.setObservation(enumObservation.ABSENCE);

        if (radioButtonAtestado.isChecked())
            registry.setObservation(enumObservation.ATESTADO);


        if (radioButtonDeclaration.isChecked()) {
            registry.setObservation(enumObservation.DECLARACAO_DE_HORAS);
            //registry.setTimeDeclaration(dateUtil.convertStringTimeToCalendar(leaveTime.getText().toString()));
        }

        if (radioButtonNothing.isChecked())
            registry.setObservation(enumObservation.NORMAL);

        registry.setDay(dateUtil.convertStringDateToCalendar(editTextDateWorked.getText().toString()));
        registry.setEntrance(dateUtil.convertStringTimeToCalendar(editTextEntranceTime.getText().toString()));
        registry.setEntranceLunch(dateUtil.convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString()));
        registry.setLeaveLunch(dateUtil.convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString()));
        registry.setLeave(dateUtil.convertStringTimeToCalendar(editTextLeaveTime.getText().toString()));
        if (!editTextPercentExtraWork.getText().toString().equals("") && !editTextPercentExtraWork.getText().toString().equals(null)) {
            registry.setPercent(Integer.parseInt(editTextPercentExtraWork.getText().toString()));
        } else {
            registry.setPercent(0);
        }

        registry.setRequiredTimeToWork(dateUtil.convertStringTimeToCalendar(editTextRequiredTime.getText().toString()));

        Dao dao = new Dao(getActivity());
        if (registryBundle != null) {
            registry.setId(registryBundle.getId());
            dao.saveModificationRegistry(registry);
        } else {
            dao.insertRegistryToDao(registry);
        }
        dao.close();


        System.out.println(registry);
    }


    private void setEditTextSetDataOrTimeFromPicker(EditText editTextSetDataOrTimeFromPicker) {
        this.editTextSetDataOrTimeFromPicker = editTextSetDataOrTimeFromPicker;
    }


}
