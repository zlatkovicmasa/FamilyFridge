package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

    @Query("SELECT m FROM MealPlan m WHERE m.date BETWEEN :startDate AND :endDate")
    List<MealPlan> findMealPlanBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<MealPlan> findAllByDate(Date date);
}
