package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "meals", schema = "family_fridge")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean approved;

    @ManyToOne
    @JoinColumn(name="suggested_by")
    private User suggestedBy;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealIngredient> mealIngredients;

    public Meal(){}
    public Meal(String name, User suggestedBy, Boolean approved) {
        this.name = name;
        this.suggestedBy = suggestedBy;
        this.approved = approved;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public User getSuggestedBy() {return suggestedBy;}
    public void setSuggestedBy(User suggestedBy) {this.suggestedBy = suggestedBy;}
    public Boolean getApproved() {return approved;}
    public void setApproved(Boolean approved) {this.approved = approved;}
    public List<MealIngredient> getMealIngredients() {return mealIngredients;}
    public void setMealIngredients(List<MealIngredient> mealIngredients) {this.mealIngredients = mealIngredients;}
    public void addMealIngredient(MealIngredient mealIngredient) {
        mealIngredients.add(mealIngredient);
    }
}
