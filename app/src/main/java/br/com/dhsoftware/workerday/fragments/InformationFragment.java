package br.com.dhsoftware.workerday.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.Money;
import br.com.dhsoftware.workerday.util.SalaryUtil;

public class InformationFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private View view;
    private TextView textViewNetSalary, textViewGrossSalary, textViewINSS, textViewIRRF, textViewDeduction, textViewFGTS, textViewResult;
    private EditText editTextOne, editTextTwo, editTextActually;


    public InformationFragment() {
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
        view = inflater.inflate(R.layout.fragment_information, container, false);

        setView();

        User user = new User(getActivity());
        SalaryUtil salaryUtil = new SalaryUtil(user);
        Money money = new Money();

        textViewINSS.setText("INSS: R$ " + money.doubleToStringMoney(String.valueOf(salaryUtil.calculateINSS())));
        textViewIRRF.setText("IRRF: R$ " + money.doubleToStringMoney(String.valueOf(salaryUtil.calculateIRRF())));
        textViewNetSalary.setText("Salario liquido: R$ " + money.doubleToStringMoney(String.valueOf(salaryUtil.calculateNetSalary())));
        textViewDeduction.setText("Deduções: R$ " + user.getUserJSON().getDeduction());
        textViewFGTS.setText("FGTS: R$ " + money.doubleToStringMoney(String.valueOf(salaryUtil.calculateFGTS())));
        textViewGrossSalary.setText("Base calculo: R$ " + money.doubleToStringMoney(String.valueOf(user.getSalary())));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setView() {
        textViewNetSalary = view.findViewById(R.id.textView_netSalary_information);
        textViewINSS = view.findViewById(R.id.textView_inss_information);
        textViewIRRF = view.findViewById(R.id.textView_irrf_information);
        textViewDeduction = view.findViewById(R.id.textView_deduction_information);
        textViewFGTS = view.findViewById(R.id.textView_fgts_information);
        textViewGrossSalary = view.findViewById(R.id.textView_grossSalary_information);
        editTextOne = view.findViewById(R.id.editText_filterOne_fragmentInformation);
        editTextOne.setOnClickListener(this);
        editTextTwo = view.findViewById(R.id.editText_filterTwo_fragmentInformation);
        editTextTwo.setOnClickListener(this);
        textViewResult = view.findViewById(R.id.textView_result_fragmentInformation);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String date = DateUtil.getInstanceDateUtil().formatCorrectStringDate(dayOfMonth, month, year);

        editTextActually.setText(date);
        if(!editTextOne.getText().toString().equals("") && editTextTwo.getText().toString().equals("")){
            calculateTotalCompTimeOrExtraTime();
        }

    }

    private void datePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editText_filterOne_fragmentInformation:
                editTextActually = editTextOne;
                datePickerDialog();
                break;
            case R.id.editText_filterTwo_fragmentInformation:
                editTextActually = editTextTwo;
                datePickerDialog();
                break;
        }
    }


    public void calculateTotalCompTimeOrExtraTime(){

    }
}
