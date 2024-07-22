package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "meal_plans", schema = "family_fridge")
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="meal_id")
    private Meal meal;

    private Date date;

    @ManyToOne
    @JoinColumn(name="time_of_day")
    private TimeOfDay timeOfDay;

    public MealPlan(){}
    public MealPlan(Meal meal, Date date, TimeOfDay timeOfDay) {
        this.meal = meal;
        this.date = date;
        this.timeOfDay = timeOfDay;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Meal getMeal() {return meal;}
    public void setMeal(Meal meal) {this.meal = meal;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public TimeOfDay getTimeOfDay() {return timeOfDay;}
    public void setTimeOfDay(TimeOfDay timeOfDay) {this.timeOfDay = timeOfDay;}
}
