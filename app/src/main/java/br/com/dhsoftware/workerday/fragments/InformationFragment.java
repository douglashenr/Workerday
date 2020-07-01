package br.com.dhsoftware.workerday.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.model.User;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.SalaryUtil;

public class InformationFragment extends Fragment {
    private View view;
    private TextView textViewNetSalary, textViewGrossSalary, textViewINSS, textViewIRRF, textViewDeduction, textViewFGTS;
    private JSONUser jsonUser;


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


        jsonUser = new JSONUser(getActivity());
        setView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = new User(getActivity());
        SalaryUtil salaryUtil = new SalaryUtil(user);
        textViewINSS.setText("INSS: R$ " + salaryUtil.calculateINSS());
        textViewIRRF.setText("IRRF: R$ " + salaryUtil.calculateIRRF());
        textViewNetSalary.setText("Salario liquido: R$ " + salaryUtil.calculateNetSalary());
        textViewDeduction.setText("Deduções: R$ " + jsonUser.getDeduction());
        textViewFGTS.setText("FGTS: R$ " + salaryUtil.calculateFGTS());
        textViewGrossSalary.setText("Base calculo: R$ " + user.getSalary());
    }

    private void setView() {
        textViewNetSalary = view.findViewById(R.id.textView_netSalary_information);
        textViewINSS = view.findViewById(R.id.textView_inss_information);
        textViewIRRF = view.findViewById(R.id.textView_irrf_information);
        textViewDeduction = view.findViewById(R.id.textView_deduction_information);
        textViewFGTS = view.findViewById(R.id.textView_fgts_information);
        textViewGrossSalary = view.findViewById(R.id.textView_grossSalary_information);
    }
}
