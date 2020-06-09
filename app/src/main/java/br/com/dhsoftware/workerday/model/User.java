package br.com.dhsoftware.workerday.model;

import java.util.ArrayList;

public class User {
    private String name; // obrigatório
    private String email; // obrigatório
    private Double salary; //verificar uma classe para substituir Double
    private Double deduction; //verificar uma classe para substituir Double
    private Double percentExtraSalary;
    private ArrayList<Registry> registries;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
