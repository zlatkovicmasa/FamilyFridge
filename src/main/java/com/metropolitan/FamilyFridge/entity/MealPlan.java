package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_plans", schema = "family_fridge")
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="meal_id")
    private Meal meal;

    private String dayOfWeek;
    private String timeOfDay;

    public MealPlan(){}
    public MealPlan(Meal meal, String dayOfWeek, String timeOfDay) {
        this.meal = meal;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Meal getMeal() {return meal;}
    public void setMeal(Meal meal) {this.meal = meal;}
    public String getDayOfWeek() {return dayOfWeek;}
    public void setDayOfWeek(String dayOfWeek) {this.dayOfWeek = dayOfWeek;}
    public String getTimeOfDay() {return timeOfDay;}
    public void setTimeOfDay(String timeOfDay) {this.timeOfDay = timeOfDay;}
}
