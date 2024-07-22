package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryRepository extends JpaRepository<Grocery, Long> {
}
