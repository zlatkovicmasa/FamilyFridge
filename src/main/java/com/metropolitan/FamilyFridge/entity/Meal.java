package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "meals", schema = "family_fridge")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String recipe;

    @ManyToOne
    @JoinColumn(name="suggested_by")
    private User suggestedBy;

    private Boolean approved;

    public Meal(){}
    public Meal(String name, String description, String recipe, User suggestedBy, Boolean approved) {
        this.name = name;
        this.description = description;
        this.recipe = recipe;
        this.suggestedBy = suggestedBy;
        this.approved = approved;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getRecipe() {return recipe;}
    public void setRecipe(String recipe) {this.recipe = recipe;}
    public User getSuggestedBy() {return suggestedBy;}
    public void setSuggestedBy(User suggestedBy) {this.suggestedBy = suggestedBy;}
    public Boolean getApproved() {return approved;}
    public void setApproved(Boolean approved) {this.approved = approved;}
}
