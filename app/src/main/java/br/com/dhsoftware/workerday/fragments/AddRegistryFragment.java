package br.com.dhsoftware.workerday.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.enumObservation;


/*
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRegistryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRegistryFragment extends Fragment implements  DatePickerDialog.OnDateSetListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener, RadioGroup.OnCheckedChangeListener
        , Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View view;
    private EditText dateWorked, entranceTime, entranceLunchTime, leaveLunchTime, leaveTime, requiredTime, percentExtraWork;
    private EditText editTextSetDataOrTimeFromPicker;
    private RadioGroup buttonGroup;
    private RadioButton radioButtonAtestado, radioButtonDeclaration, radioButtonNothing, radioButtonAbsence;
    private User user;
    private Registry registry;
    private enumObservation observation;

    private DialogUtil dialogUtil;

    public AddRegistryFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRegistryFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static AddRegistryFragment newInstance(String param1, String param2) {
        AddRegistryFragment fragment = new AddRegistryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

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
        setView();


        dialogUtil = new DialogUtil(getActivity());



        return view;
    }

    private void setView(){
        dateWorked = view.findViewById(R.id.editText_dateWorked_registry);
        dateWorked.setOnClickListener(this);

        entranceTime = view.findViewById(R.id.editText_entranceTime_registry);
        entranceTime.setOnClickListener(this);

        entranceLunchTime = view.findViewById(R.id.editText_entranceLunchTime_registry);
        entranceLunchTime.setOnClickListener(this);

        leaveLunchTime = view.findViewById(R.id.editText_leaveLunchTime_registry);
        leaveLunchTime.setOnClickListener(this);

        leaveTime = view.findViewById(R.id.editText_leaveTime_registry);
        leaveTime.setOnClickListener(this);

        requiredTime = view.findViewById(R.id.editText_requiredTimeToWorkTime_registry);
        requiredTime.setOnClickListener(this);

        percentExtraWork = view.findViewById(R.id.editText_porcentExtraWork_registry);
        percentExtraWork.setOnClickListener(this);

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
        if(!jsonUser.getPercentExtraSalary().equals(""))
            percentExtraWork.setText(jsonUser.getPercentExtraSalary());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ) {
        String date = dayOfMonth + "/" + month + "/" + year;
        editTextSetDataOrTimeFromPicker.setText(date);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        editTextSetDataOrTimeFromPicker.setText(time);
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonAtestado.getId()))){
            enableEditText(false);
            observation = enumObservation.ATESTADO;
        }
        else
            enableEditText(true);

        if(String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonDeclaration.getId()))){
            observation = enumObservation.DECLARACAO_DE_HORAS;
        }

        if(String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonAbsence.getId()))){
            observation = enumObservation.ABSENCE;
            enableEditText(false);
        }

        if(String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonNothing.getId()))){
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

        switch (v.getId()){
            case R.id.editText_dateWorked_registry:
                setEditTextSetDataOrTimeFromPicker(dateWorked);
                datePickerDialog();
                break;

            case R.id.editText_entranceTime_registry:
                setEditTextSetDataOrTimeFromPicker(entranceTime);
                timePickerDialog();
                break;

            case R.id.editText_entranceLunchTime_registry:
                setEditTextSetDataOrTimeFromPicker(entranceLunchTime);
                timePickerDialog();
                break;
            case R.id.editText_leaveLunchTime_registry:
                setEditTextSetDataOrTimeFromPicker(leaveLunchTime);
                timePickerDialog();
                break;
            case R.id.editText_leaveTime_registry:
                setEditTextSetDataOrTimeFromPicker(leaveTime);
                timePickerDialog();
                break;
            case R.id.editText_requiredTimeToWorkTime_registry:
                timePickerDialog();
                setEditTextSetDataOrTimeFromPicker(requiredTime);
                break;
            case R.id.button_save_registry:
                validator();
                break;
        }
    }

    private void timePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this,  calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void datePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        //calendar.setTime(date);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void enableEditText(boolean active){
        entranceTime.setEnabled(active);
        entranceLunchTime.setEnabled(active);
        leaveTime.setEnabled(active);
        leaveLunchTime.setEnabled(active);
        requiredTime.setEnabled(active);
        percentExtraWork.setEnabled(active);
    }



    private void validator(){
            if(dateWorked.getText().toString().equals("")){
                System.out.println("Não salvou porque DataWorked está vazio");
                dateWorked.setHintTextColor(Color.RED);
                dialogUtil.infoDialog("Complete o campo em vermelho para salvar!");
            }else {
                System.out.println("Salvou!");
                dialogUtil.showToast("Ponto salvo!", Toast.LENGTH_SHORT);
                createObjectRegistry();
                goToFragmentListViewMain();
            }
    }

    private void createObjectRegistry() {
        registry = new Registry();
        DateUtil dateUtil = new DateUtil();
        if(radioButtonAtestado.isChecked() || radioButtonAbsence.isChecked()){
            if(radioButtonAbsence.isChecked())
                registry.setObservation(enumObservation.ABSENCE);

            if(radioButtonAtestado.isChecked())
                registry.setObservation(enumObservation.ATESTADO);

        }


        if(radioButtonDeclaration.isChecked()) {
            registry.setObservation(enumObservation.DECLARACAO_DE_HORAS);
            //registry.setTimeDeclaration(dateUtil.convertStringTimeToCalendar(leaveTime.getText().toString()));
        }

        if(radioButtonNothing.isChecked())
            registry.setObservation(enumObservation.NORMAL);

        registry.setDay(dateUtil.convertStringDateToCalendar(dateWorked.getText().toString()));
        registry.setEntrance(dateUtil.convertStringTimeToCalendar(entranceTime.getText().toString()));
        registry.setEntranceLunch(dateUtil.convertStringTimeToCalendar(entranceLunchTime.getText().toString()));
        registry.setLeaveLunch(dateUtil.convertStringTimeToCalendar(leaveLunchTime.getText().toString()));
        registry.setLeave(dateUtil.convertStringTimeToCalendar(leaveTime.getText().toString()));
        registry.setPercent(Integer.parseInt(percentExtraWork.getText().toString()));
        registry.setRequiredTimeToWork(dateUtil.convertStringTimeToCalendar(requiredTime.getText().toString()));



        Dao dao = new Dao(getActivity());
        dao.insertRegistryToDao(registry);

        System.out.println(registry);
    }

    private void goToFragmentListViewMain(){
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.popBackStack();
    }


    public EditText getEditTextSetDataOrTimeFromPicker() {
        return editTextSetDataOrTimeFromPicker;
    }

    private void setEditTextSetDataOrTimeFromPicker(EditText editTextSetDataOrTimeFromPicker) {
        this.editTextSetDataOrTimeFromPicker = editTextSetDataOrTimeFromPicker;
    }



}
