package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory", schema = "family_frigde")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grocery_id")
    private Grocery grocery;
    private int quantity;

    public Long getId() {return this.id;}
    public void setId(Long id) { this.id = id;}
    public Grocery getGrocery() {return this.grocery;}
    public void setGrocery(Grocery grocery) {this.grocery = grocery;}
    public int getQuantity() {return this.quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}
