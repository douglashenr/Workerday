package br.com.dhsoftware.workerday.model;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import br.com.dhsoftware.workerday.util.DoubleUtil;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.discount.Deduction;

public class User implements Serializable {
    private GrossSalary grossSalary; //verificar uma classe para substituir Double
    private double deduction = 0.0; //verificar uma classe para substituir Double
    private int percentExtraSalary = 0;
    private int timeForWeek;
    private ArrayList<Registry> registries;
    private Context context;
    private JSONUser userJSON;
    private boolean transportation;
    private boolean compTime;
    private DoubleUtil doubleUtil;

    public JSONUser getUserJSON() {
        return userJSON;
    }


    public User(Context context) {
        this.context = context;
        doubleUtil = new DoubleUtil();

        setUserFromJSON();
    }

    private void setUserFromJSON() {
        userJSON = new JSONUser(context);
        try {
            //if(userJSON.getDeduction().equals(""))
            if(userJSON.getDeduction().equals(""))
                setDeduction(0.00);
            else
            setDeduction(new Deduction().moneyToDouble(userJSON.getDeduction()).doubleValue());
            setCompTime(userJSON.getCompTime());
            setGrossSalary(new GrossSalary(userJSON.getSalary()));
            setTimeForWeek(Integer.parseInt(userJSON.getTimeForWeek()));
            if (userJSON.getPercentExtraSalary().equals("")) {
                setPercentExtraSalary(0);
            } else {
                setPercentExtraSalary(Integer.parseInt(userJSON.getPercentExtraSalary()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //setTransportation(userJSON.getTransportation());
    }

    public boolean isTransportation() {
        return transportation;
    }

    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
    }

    private void setPercentExtraSalary(int percentExtraSalary) {
        this.percentExtraSalary = percentExtraSalary;
    }

    public boolean isCompTime() {
        return compTime;
    }

    public void setCompTime(boolean compTime) {
        this.compTime = compTime;
    }

    public GrossSalary getGrossSalary() {
        return grossSalary;
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
        grossSalary.setTimeForWeek(timeForWeek);
    }

    public void setGrossSalary(GrossSalary grossSalary) {
        this.grossSalary = grossSalary;
    }

    public ArrayList<Registry> getRegistries() {
        return registries;
    }

    public void setRegistries(ArrayList<Registry> registries) {
        this.registries = registries;
    }
}
