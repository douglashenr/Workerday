package br.com.dhsoftware.workerday.model;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.Money;

public class User implements Serializable {
    private double salary; //verificar uma classe para substituir Double
    private double deduction = 0.0; //verificar uma classe para substituir Double
    private int percentExtraSalary = 0;
    private int timeForWeek;
    private double salaryPerHour;
    private ArrayList<Registry> registries;
    private Context context;

    public User(Context context) {
        this.context = context;

        setUserFromJSON();

    }

    private void setUserFromJSON() {
        Money money = new Money();
        JSONUser userJSON = new JSONUser(context);
        try {
            setDeduction(money.dinheiroParaDouble(userJSON.getDeduction(), Locale.FRANCE).doubleValue());
            setSalary(money.dinheiroParaDouble(userJSON.getSalary(), Locale.FRANCE).doubleValue());
            setTimeForWeek(Integer.parseInt(userJSON.getTimeForWeek()));
            setSalaryPerHour(money.doubleComDoisDecimais(Double.valueOf(userJSON.getSalaryPerHour())));
            setPercentExtraSalary(Integer.parseInt(userJSON.getPercentExtraSalary()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setPercentExtraSalary(int percentExtraSalary) {
        this.percentExtraSalary = percentExtraSalary;
    }


    public double getSalary() {
        return salary;
    }

    private void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getDeduction() {
        return deduction;
    }

    private void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public int getTimeForWeek() {
        return timeForWeek;
    }

    private void setTimeForWeek(int timeForWeek) {
        this.timeForWeek = timeForWeek;
    }

    public Double getSalaryPerHour() {
        return salaryPerHour;
    }

    private void setSalaryPerHour(Double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public ArrayList<Registry> getRegistries() {
        return registries;
    }

    public void setRegistries(ArrayList<Registry> registries) {
        this.registries = registries;
    }
}
