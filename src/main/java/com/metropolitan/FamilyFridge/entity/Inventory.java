package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory", schema = "family_fridge")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grocery_id")
    private Grocery grocery;

    private double quantity;

    public Long getId() {return this.id;}
    public void setId(Long id) { this.id = id;}
    public Grocery getGrocery() {return this.grocery;}
    public void setGrocery(Grocery grocery) {this.grocery = grocery;}
    public double getQuantity() {return this.quantity;}
    public void setQuantity(double quantity) {this.quantity = quantity;}
}
