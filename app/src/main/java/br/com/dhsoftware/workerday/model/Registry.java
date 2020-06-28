package br.com.dhsoftware.workerday.model;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.enumObservation;

public class Registry implements Serializable {
    private Calendar day = null;
    private Calendar entrance = null;
    private Calendar entranceLunch = null;
    private Calendar leaveLunch = null;
    private Calendar leave = null;
    private Calendar requiredTimeToWork = null;
    private int percent;
    private enumObservation observation;
    private Calendar timeDeclaration;
    private Long id;

    public Registry(Calendar day, enumObservation observation) {

        this.day = day;
        this.observation = observation;
    }

    public Registry(Calendar day, Calendar entrance, enumObservation observation){

        this.day = day;
        this.entrance = entrance;
        this.observation = observation;
    }

    public Registry() {
    }

    public String getDayString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringDate(day);
    }
    public String getEntranceString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringTime(getEntrance());
    }
    public String getEntranceLunchString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringTime(getEntranceLunch());
    }
    public String getLeaveLunchString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringTime(getLeaveLunch());
    }
    public String getLeaveString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringTime(getLeave());
    }
    public String getRequiredTimeToWorkString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringTime(getRequiredTimeToWork());
    }
    public String getTimeDeclarationString() {
        return DateUtil.getInstanceDateUtil().convertCalendarToStringTime(getTimeDeclaration());
    }
    public String getObservationString(){
        return getObservation().toString();
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
    public int getPercent() {
        return percent;
    }
    public void setPercent(int percent) {
        this.percent = percent;
    }
    public enumObservation getObservation() {
        return observation;
    }
    public void setObservation(enumObservation observation) {
        this.observation = observation;
    }


    public void setObservation(String observation){
        if(observation.equals(enumObservation.ATESTADO.toString())) {
            this.observation = enumObservation.ATESTADO;
        }
        if(observation.equals(enumObservation.DECLARACAO_DE_HORAS.toString())){
            this.observation = enumObservation.DECLARACAO_DE_HORAS;
        }
        if(observation.equals(enumObservation.NORMAL.toString())){
            this.observation = enumObservation.NORMAL;
        }
        if(observation.equals(enumObservation.ABSENCE.toString())){
            this.observation = enumObservation.ABSENCE;
        }
    }

    public Calendar getTimeDeclaration() {
        return timeDeclaration;
    }
    public void setTimeDeclaration(Calendar timeDeclaration) {
        this.timeDeclaration = timeDeclaration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Registro: " + day.getTime();
    }




    public static ArrayList<Registry> testArrayList(Context context){

        Dao dao = new Dao(context);
        return dao.getRegistryFromDao();

        /*
        ArrayList<Registry> registries;
        registries = new ArrayList<Registry>();

        registries.add(new Registry(DateUtil.getInstanceDateUtil().convertStringDateToCalendar("25/12/1998"), enumObservation.ATESTADO));
        registries.add(new Registry(DateUtil.getInstanceDateUtil().convertStringDateToCalendar("19/02/2008"),
                DateUtil.getInstanceDateUtil().convertStringDataAndTimeToCalendar("19/02/2008", "13:15"), enumObservation.NORMAL));

        registries.get(1).setLeave(DateUtil.getInstanceDateUtil().convertStringDataAndTimeToCalendar("19/02/2008", "18:15"));

        return registries;*/

    }
}
