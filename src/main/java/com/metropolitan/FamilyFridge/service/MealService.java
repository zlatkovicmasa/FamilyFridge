package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.repository.MealIngredientRepository;
import com.metropolitan.FamilyFridge.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MealIngredientRepository mealIngredientRepository;


    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Meal getMealById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    public void save(Meal meal) {
        mealRepository.save(meal);
    }
}
