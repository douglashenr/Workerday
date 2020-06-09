package br.com.dhsoftware.workerday.model;

import androidx.annotation.NonNull;

import java.util.Calendar;

import br.com.dhsoftware.workerday.util.enumObservation;

public class Registry {
    private User user;
    private Calendar day;
    private Calendar entrance;
    private Calendar entranceLunch;
    private Calendar leaveLunch;
    private Calendar leave;
    private Calendar requiredTimeToWork;
    private Double percent;
    private enumObservation observation;


    public Registry(User user, Calendar day, enumObservation observation) {
        this.user = user;
        this.day = day;
        this.day = day;
    }

    public Registry(User user, Calendar day, Calendar entrance, enumObservation observation){
        this.user = user;
        this.day = day;
        this.entrance = entrance;
        this.observation = observation;
    }

    @NonNull
    @Override
    public String toString() {
        return "Registro: " + day.getTime();
    }
}
