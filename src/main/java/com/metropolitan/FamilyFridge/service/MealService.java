package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealIngredient;
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

    public void addMeal(Meal meal) {
        mealRepository.save(meal);
    }

    public void addIngredientToMeal(Long mealId, Grocery grocery, double quantity) {
        Meal meal = mealRepository.findById(mealId).orElseThrow();
        MealIngredient mealIngredient = new MealIngredient(meal, grocery, quantity);
        mealIngredientRepository.save(mealIngredient);

    }
}
