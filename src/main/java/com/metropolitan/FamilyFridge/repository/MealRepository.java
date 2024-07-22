package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
