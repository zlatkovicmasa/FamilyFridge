package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
}
