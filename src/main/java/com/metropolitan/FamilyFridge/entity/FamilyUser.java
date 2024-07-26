package com.metropolitan.FamilyFridge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "family_fridge")
public class FamilyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;

    public FamilyUser() {}
    public FamilyUser(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public Boolean isAdmin() {return role.equals("ROLE_ADMIN");}
}
