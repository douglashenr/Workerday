package br.com.dhsoftware.workerday.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.DatePickerUtil;
import br.com.dhsoftware.workerday.util.SalaryUtil;
import br.com.dhsoftware.workerday.util.discount.FGTS;
import br.com.dhsoftware.workerday.util.discount.INSS;
import br.com.dhsoftware.workerday.util.discount.IRRF;

public class InformationFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView textViewNetSalary, textViewGrossSalary, textViewINSS, textViewIRRF, textViewDeduction, textViewFGTS, textViewResult;
    private EditText editTextOne, editTextTwo;
    private DatePickerUtil datePickerUtil;


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

        textViewINSS.setText("INSS: R$ " + salaryUtil.calculateDiscountToString(user.getGrossSalary().getGrossSalary(), new INSS()));
        textViewIRRF.setText("IRRF: R$ " + salaryUtil.calculateDiscountToString(user.getGrossSalary().getGrossSalary(), new IRRF(new INSS())));
        textViewNetSalary.setText("Salario liquido: R$ " + user.getGrossSalary().doubleToStringMoney(salaryUtil.calculateNetSalary()));
        textViewDeduction.setText("Deduções: R$ " + user.getUserJSON().getDeduction());
        textViewFGTS.setText("FGTS: R$ " + salaryUtil.calculateDiscountToString(user.getGrossSalary().getGrossSalary(), new FGTS()));
        textViewGrossSalary.setText("Base calculo: R$ " + user.getGrossSalary().doubleToStringMoney(user.getGrossSalary().getGrossSalary()));

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

//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        month++;
//        String date = DateUtil.getInstanceDateUtil().formatCorrectStringDate(dayOfMonth, month, year);
//
//        editTextActually.setText(date);
//        if(!editTextOne.getText().toString().equals("") && editTextTwo.getText().toString().equals("")){
//            calculateTotalCompTimeOrExtraTime();
//        }
//
//    }
//

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editText_filterOne_fragmentInformation:
                datePickerUtil.datePickerDialog(getActivity(), editTextOne, null);
                break;
            case R.id.editText_filterTwo_fragmentInformation:
                datePickerUtil.datePickerDialog(getActivity(), editTextTwo, null);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        datePickerUtil = new DatePickerUtil();
    }

    public void calculateTotalCompTimeOrExtraTime(){

    }
}
