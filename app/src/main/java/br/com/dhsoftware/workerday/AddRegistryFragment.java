package br.com.dhsoftware.workerday;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.enumObservation;


/**
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
    private RadioButton radioButtonAtestado, radioButtonDeclaration, radioButtonNothing;
    private User user;
    private Registry registry;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRegistryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRegistryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRegistryFragment newInstance(String param1, String param2) {
        AddRegistryFragment fragment = new AddRegistryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_registry, container, false);

        setView();

        return view;
    }

    private void setView(){
        dateWorked = view.findViewById(R.id.date_worked);
        dateWorked.setOnClickListener(this);

        entranceTime = view.findViewById(R.id.entrance_time);
        entranceTime.setOnClickListener(this);

        entranceLunchTime = view.findViewById(R.id.entrance_lunch_time);
        entranceLunchTime.setOnClickListener(this);

        leaveLunchTime = view.findViewById(R.id.leave_lunch_time);
        leaveLunchTime.setOnClickListener(this);

        leaveTime = view.findViewById(R.id.leave_time);
        leaveTime.setOnClickListener(this);

        requiredTime = view.findViewById(R.id.required_time_to_work_time);
        requiredTime.setOnClickListener(this);

        percentExtraWork = view.findViewById(R.id.porcent_extra_work);
        percentExtraWork.setOnClickListener(this);

        buttonGroup = view.findViewById(R.id.button_group);
        buttonGroup.setOnCheckedChangeListener(this);

        radioButtonAtestado = view.findViewById(R.id.button_atestado);
        radioButtonDeclaration = view.findViewById(R.id.button_time_declaration);
        radioButtonNothing = view.findViewById(R.id.button_nothing);

        ImageButton saveButton = view.findViewById(R.id.button_save_registry);
        saveButton.setOnClickListener(this);

        //completeAutomaticPercentField();
    }

    private void completeAutomaticPercentField() {
        if(user.getPercentExtraSalary() != 0)
            percentExtraWork.setText(String.valueOf(user.getPercentExtraSalary()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ) {
        editTextSetDataOrTimeFromPicker.setText(dayOfMonth + "/" + month + "/" + year);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        editTextSetDataOrTimeFromPicker.setText(String.format("%d:%d", hourOfDay, minute));
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
        if(radioButtonAtestado.isChecked()){
            if(dateWorked.getText().toString().equals("")){
                System.out.println("Não salvou porque DataWorked está vazio");
                dateWorked.setTextColor(Color.RED);
                entranceTime.setHintTextColor(Color.GRAY);
            }else {
                System.out.println("Salvou com atestado");
                createObjectRegistryAtestado();
                goToFragmentListViewMain();

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

    private void createObjectRegistryAtestado() {
        registry = new Registry(user, DateUtil.getInstanceDateUtil().convertStringDataToCalendar(dateWorked.getText().toString()), enumObservation.ATESTADO);
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
