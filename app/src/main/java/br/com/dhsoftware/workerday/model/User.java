package br.com.dhsoftware.workerday.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import br.com.dhsoftware.workerday.util.JSONUser;

public class User implements Serializable {
    private String name;
    private String email;
    private Double salary; //verificar uma classe para substituir Double
    private Double deduction = 0.0; //verificar uma classe para substituir Double
    private int percentExtraSalary = 0;
    private int timeForWeek;
    private Double salaryPerHour;
    private ArrayList<Registry> registries;
    private Context context;

    public User(Context context) {
        this.context = context;

        setUserFromJSON();

    }

    private void setUserFromJSON(){
        JSONUser userJSON = new JSONUser(context);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userJSON.read());
            setDeduction(jsonObject.getDouble("deduction"));
            setSalary(jsonObject.getDouble("salary"));
            setTimeForWeek(jsonObject.getInt("timeForWeek"));
            setSalaryPerHour(jsonObject.getDouble("salaryPerHour"));
            setPercentExtraSalary(jsonObject.getInt("percentExtraSalary"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public int getPercentExtraSalary() {
        return percentExtraSalary;
    }

    public void setPercentExtraSalary(int percentExtraSalary) {
        this.percentExtraSalary = percentExtraSalary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public int getTimeForWeek() {
        return timeForWeek;
    }

    public void setTimeForWeek(int timeForWeek) {
        this.timeForWeek = timeForWeek;
    }

    public Double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(Double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public ArrayList<Registry> getRegistries() {
        return registries;
    }

    public void setRegistries(ArrayList<Registry> registries) {
        this.registries = registries;
    }
}
