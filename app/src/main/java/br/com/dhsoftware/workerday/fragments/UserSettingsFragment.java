package br.com.dhsoftware.workerday.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import br.com.dhsoftware.workerday.FragmentController;
import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.textWatcher.MoneyTextWatcher;


public class UserSettingsFragment extends Fragment implements View.OnClickListener {
    private View view;
    private EditText editTextSalary, editTextDeduction, editTextTimePerWeek, editTextPercentExtraSalary;
    private ImageButton imageButtonSaveUser;
    private JSONUser jsonUser;
    private DialogUtil dialogUtil;
    private FragmentController fragmentController;
    private CheckBox checkBoxCompTime;

    public UserSettingsFragment() {
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
        view = inflater.inflate(R.layout.fragment_user_settings, container, false);
        setView();
        fragmentController = new FragmentController(getFragmentManager());
        dialogUtil = new DialogUtil(getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        jsonUser = new JSONUser(getActivity());

        setEditTextFromJSONUser();
    }


    private void setEditTextFromJSONUser() {
        editTextSalary.setText(jsonUser.getSalary());
        editTextDeduction.setText(jsonUser.getDeduction());
        editTextPercentExtraSalary.setText(jsonUser.getPercentExtraSalary());
        editTextTimePerWeek.setText(jsonUser.getTimeForWeek());
        imageButtonSaveUser.setOnClickListener(this);
        checkBoxCompTime.setChecked(jsonUser.getCompTime());
    }

    private void setView() {
        editTextSalary = view.findViewById(R.id.editText_salary_userSettings);
        editTextSalary.addTextChangedListener(new MoneyTextWatcher(editTextSalary));
        editTextDeduction = view.findViewById(R.id.editText_deduction_userSettings);
        editTextDeduction.addTextChangedListener(new MoneyTextWatcher(editTextDeduction));
        editTextTimePerWeek = view.findViewById(R.id.editText_timeForWeek_userSettings);
        editTextPercentExtraSalary = view.findViewById(R.id.editText_percentExtraSalary_userSettings);
        imageButtonSaveUser = view.findViewById(R.id.button_save_userSettings);
        checkBoxCompTime = view.findViewById(R.id.checkBox_compensatoryTimeOff_userSettings);
    }


    @Override
    public void onClick(View v) {
        saveUserInJSONUser();
        dialogUtil.showToast("Configurações salvas!", Toast.LENGTH_LONG);
        fragmentController.goBackFragment();
    }

    private void saveUserInJSONUser() {
        jsonUser.setSalaryJSON(editTextSalary.getText().toString());
        jsonUser.setPercentExtraSalaryJSON(editTextPercentExtraSalary.getText().toString());
        jsonUser.setDeductionJSON(editTextDeduction.getText().toString());
        jsonUser.setTimeForWeekJSON(editTextTimePerWeek.getText().toString());
        jsonUser.setCompTime(String.valueOf(checkBoxCompTime.isChecked()));
    }
}
