package br.com.dhsoftware.workerday.util;

import java.text.ParseException;
import java.util.Locale;

import br.com.dhsoftware.workerday.model.User;

public class SalaryUtil {

    private Money money;
    private User user;

    public SalaryUtil(User user) {
        money = new Money();
        this.user = user;
    }

    public SalaryUtil() {
        money = new Money();
    }

    public Double calculateSalaryPerHour(String salary, int hourForWeek) {
        double moneyPerHour = 0.0;
        try {
            moneyPerHour = money.dinheiroParaDouble(salary, Locale.FRANCE).doubleValue() / (hourForWeek * 5);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return money.doubleComDoisDecimais(moneyPerHour);
    }

    public double calculateINSS() {
        double salary = user.getSalary();
        System.out.println("salario em calculateINSS = " + salary);
        if (salary <= 1045) {
            return money.doubleComDoisDecimais(salary * 0.075);
        } else if (salary > 1045 && salary <= 2080.60) {
            return money.doubleComDoisDecimais(((salary - 1045) * 0.09) + 78.37);
        } else if (salary > 2080.60 && salary <= 3134.40) {
            return money.doubleComDoisDecimais(((salary - 2080.60) * 0.12) + 78.37 + 94.01);
        } else if (salary > 3134.40 && salary <= 6101.06) {
            return money.doubleComDoisDecimais(((salary - 3134.40) * 0.14) + 78.37 + 94.01 + 125.37);
        } else {
            return 713.09;
        }
    }

    public double calculateIRRF() {
        System.out.println("SALARIO DO USUARIO: " + user.getSalary());
        double salary = user.getSalary() - calculateINSS();
        if (salary <= 1903.98) {
            return 0.00;
        } else if (salary > 1903.98 && salary <= 2826.65) {
            return money.doubleComDoisDecimais((salary * 0.075) - 142.80);
        } else if (salary > 2826.65 && salary <= 3751.05) {
            return money.doubleComDoisDecimais((salary * 0.15) - 354.80);
        } else if (salary > 3751.05 && salary <= 4664.68) {
            return money.doubleComDoisDecimais((salary * 0.225) - 636.13);
        } else {
            return money.doubleComDoisDecimais((salary * 0.275) - 869.36);
        }
    }


    public Double calculateNetSalary() {
        return money.doubleComDoisDecimais(user.getSalary() - (calculateIRRF() + user.getDeduction() + calculateINSS()));
    }

    public double calculateFGTS() {
        return money.doubleComDoisDecimais(user.getSalary() * 0.08);
    }

}
