package br.com.dhsoftware.workerday.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.enumObservation;

public class Registry implements Serializable {
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
        this.observation = observation;
    }

    public Registry(User user, Calendar day, Calendar entrance, enumObservation observation){
        this.user = user;
        this.day = day;
        this.entrance = entrance;
        this.observation = observation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDayString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringDate(day);
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public Calendar getEntrance() {
        return entrance;
    }

    public void setEntrance(Calendar entrance) {
        this.entrance = entrance;
    }

    public Calendar getEntranceLunch() {
        return entranceLunch;
    }

    public void setEntranceLunch(Calendar entranceLunch) {
        this.entranceLunch = entranceLunch;
    }

    public Calendar getLeaveLunch() {
        return leaveLunch;
    }

    public void setLeaveLunch(Calendar leaveLunch) {
        this.leaveLunch = leaveLunch;
    }

    public Calendar getLeave() {
        return leave;
    }

    public void setLeave(Calendar leave) {
        this.leave = leave;
    }

    public Calendar getRequiredTimeToWork() {
        return requiredTimeToWork;
    }

    public void setRequiredTimeToWork(Calendar requiredTimeToWork) {
        this.requiredTimeToWork = requiredTimeToWork;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public enumObservation getObservation() {
        return observation;
    }

    public void setObservation(enumObservation observation) {
        this.observation = observation;
    }

    @NonNull
    @Override
    public String toString() {
        return "Registro: " + day.getTime();
    }
}
