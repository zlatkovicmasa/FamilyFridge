package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_ingredients", schema = "family_fridge")
public class MealIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "grocery_id")
    private Grocery grocery;

    private double quantity;

    public MealIngredient() {}
    public MealIngredient(Meal meal, Grocery grocery, double quantity) {
        this.meal = meal;
        this.grocery = grocery;
        this.quantity = quantity;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Meal getMeal() {return meal;}
    public Grocery getGrocery() {return grocery;}
    public double getQuantity() {return quantity;}
    public void setQuantity(double quantity) {this.quantity = quantity;}

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void setGrocery(Grocery grocery) {
        this.grocery = grocery;
    }
}
