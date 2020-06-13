package br.com.dhsoftware.workerday.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name; // obrigatório
    private String email; // obrigatório
    private Double salary; //verificar uma classe para substituir Double
    private Double deduction = 0.0; //verificar uma classe para substituir Double
    private int percentExtraSalary = 0;
    private ArrayList<Registry> registries;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
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
}
