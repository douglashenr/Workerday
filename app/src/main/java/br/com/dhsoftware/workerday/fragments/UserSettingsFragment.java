package br.com.dhsoftware.workerday.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Objects;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.textWatcher.MoneyTextWatcher;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSettingsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private View view;
    private EditText editTextSalary, editTextDeduction, editTextTimePerWeek, editTextPercentExtraSalary;
    private ImageButton imageButtonSaveUser;
    private JSONUser jsonUser;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSettingsFragment newInstance(String param1, String param2) {
        UserSettingsFragment fragment = new UserSettingsFragment();
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
        view = inflater.inflate(R.layout.fragment_user_settings, container, false);
        setView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        jsonUser = new JSONUser(getActivity());

        setEditTextFromJSONUser();
    }


    private void setEditTextFromJSONUser(){
        editTextSalary.setText(jsonUser.getSalary());
        editTextDeduction.setText(jsonUser.getDeduction());
        editTextPercentExtraSalary.setText(jsonUser.getPercentExtraSalary());
        editTextTimePerWeek.setText(jsonUser.getTimeForWeek());
        imageButtonSaveUser.setOnClickListener(this);
    }

    private void setView(){
        editTextSalary = view.findViewById(R.id.editText_salary_userSettings);
        editTextSalary.addTextChangedListener(new MoneyTextWatcher(editTextSalary));
        editTextDeduction = view.findViewById(R.id.editText_deduction_userSettings);
        editTextDeduction.addTextChangedListener(new MoneyTextWatcher(editTextDeduction));
        editTextTimePerWeek = view.findViewById(R.id.editText_timeForWeek_userSettings);
        editTextPercentExtraSalary = view.findViewById(R.id.editText_percentExtraSalary_userSettings);
        imageButtonSaveUser = view.findViewById(R.id.button_save_userSettings);
    }

    private void goToFragmentListViewMain(){
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void onClick(View v) {
        saveUserInJSONUser();
        goToFragmentListViewMain();
    }

    private void saveUserInJSONUser(){
        jsonUser.setSalaryJSON(editTextSalary.getText().toString());
        jsonUser.setPercentExtraSalaryJSON(editTextPercentExtraSalary.getText().toString());
        jsonUser.setDeductionJSON(editTextDeduction.getText().toString());
        jsonUser.setTimeForWeekJSON(editTextTimePerWeek.getText().toString());
    }
}
