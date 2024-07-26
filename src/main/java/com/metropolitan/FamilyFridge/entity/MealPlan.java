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
    @JoinColumn(name="time_of_day_id")
    private TimeOfDay timeOfDay;

    @ManyToOne
    @JoinColumn(name = "suggested_by")
    private FamilyUser suggestedBy;

    private Boolean accepted;

    public MealPlan(){}
    public MealPlan(Meal meal, Date date, TimeOfDay timeOfDay, FamilyUser suggestedBy, Boolean accepted) {
        this.meal = meal;
        this.date = date;
        this.timeOfDay = timeOfDay;
        this.suggestedBy = suggestedBy;
        this.accepted = accepted;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Meal getMeal() {return meal;}
    public void setMeal(Meal meal) {this.meal = meal;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public TimeOfDay getTimeOfDay() {return timeOfDay;}
    public void setTimeOfDay(TimeOfDay timeOfDay) {this.timeOfDay = timeOfDay;}
    public FamilyUser getSuggestedBy() {return suggestedBy;}
    public void setSuggestedBy(FamilyUser suggestedBy) {this.suggestedBy = suggestedBy;}
    public Boolean getAccepted() {return accepted;}
    public void setAccepted(Boolean accepted) {this.accepted = accepted;}
}
