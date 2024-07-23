package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealIngredient;
import com.metropolitan.FamilyFridge.service.GroceryService;
import com.metropolitan.FamilyFridge.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping ("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @Autowired
    private GroceryService groceryService;

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals", mealService.getAllMeals());
        model.addAttribute("groceries", groceryService.getAll());
        model.addAttribute("meal", new Meal());
        return "meals";
    }

    @PostMapping("/add")
    public String addMeal(@ModelAttribute Meal meal, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "meals";
        }

        if (meal.getMealIngredients() == null) {
            meal.setMealIngredients(new ArrayList<>());
        }

        mealService.addMeal(meal);

//        Grocery grocery = groceryService.getById(groceryId);
//        if (grocery == null) {
//            return "error";
//        }
//
//        MealIngredient ingredient = new MealIngredient();
//        ingredient.setGrocery(grocery);
//        ingredient.setQuantity(quantity);
//        ingredient.setMeal(meal);
//
//        List<MealIngredient> ingredients = new ArrayList<>();
//        ingredients.add(ingredient);
//
//        meal.setMealIngredients(ingredients);
//        mealService.addMeal(meal);

        return "redirect:/meals";
    }
}
