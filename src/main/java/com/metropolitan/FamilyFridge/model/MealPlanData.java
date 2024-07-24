package com.metropolitan.FamilyFridge.model;

import com.metropolitan.FamilyFridge.entity.TimeOfDay;

import java.sql.Date;

public class MealPlanData {

    private TimeOfDay timeOfDay;
    private Date date;

    public MealPlanData(TimeOfDay timeOfDay, Date date) {
        this.timeOfDay = timeOfDay;
        this.date = date;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
