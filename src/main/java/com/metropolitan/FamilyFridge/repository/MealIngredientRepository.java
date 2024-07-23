package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.MealIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealIngredientRepository extends JpaRepository<MealIngredient, Long> {
}
