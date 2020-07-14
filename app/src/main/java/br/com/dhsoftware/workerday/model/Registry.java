package br.com.dhsoftware.workerday.model;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.util.DateUtil;

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
    private String imageEntrance, imageLeave, imageEntranceLunch, imageLeaveLunch;


    public Registry() {
    }


    public String getImageEntrance() {
        return imageEntrance;
    }

    public void setImageEntrance(String imageEntrance) {
        this.imageEntrance = imageEntrance;
    }

    public String getImageLeave() {
        return imageLeave;
    }

    public void setImageLeave(String imageLeave) {
        this.imageLeave = imageLeave;
    }

    public String getImageEntranceLunch() {
        return imageEntranceLunch;
    }

    public void setImageEntranceLunch(String imageEntranceLunch) {
        this.imageEntranceLunch = imageEntranceLunch;
    }

    public String getImageLeaveLunch() {
        return imageLeaveLunch;
    }

    public void setImageLeaveLunch(String imageLeaveLunch) {
        this.imageLeaveLunch = imageLeaveLunch;
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

    public String getObservationString() {
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


    public void setObservation(String observation) {
        if (observation.equals(enumObservation.ATESTADO.toString())) {
            this.observation = enumObservation.ATESTADO;
        }
        if (observation.equals(enumObservation.DECLARACAO_DE_HORAS.toString())) {
            this.observation = enumObservation.DECLARACAO_DE_HORAS;
        }
        if (observation.equals(enumObservation.NORMAL.toString())) {
            this.observation = enumObservation.NORMAL;
        }
        if (observation.equals(enumObservation.ABSENCE.toString())) {
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


    public static ArrayList<Registry> getRegistryArray(Context context) {

        Dao dao = new Dao(context);
        ArrayList<Registry> arrayList = dao.getRegistryFromDao();
        dao.close();
        Collections.sort(arrayList, new Comparator<Registry>() {
            @Override
            public int compare(Registry o1, Registry o2) {
                return o1.getDay().compareTo(o2.getDay());
            }
        });
        return arrayList;


    }
}
