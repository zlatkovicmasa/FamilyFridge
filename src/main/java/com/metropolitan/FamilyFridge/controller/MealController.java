package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealIngredient;
import com.metropolitan.FamilyFridge.repository.MealIngredientRepository;
import com.metropolitan.FamilyFridge.service.GroceryService;
import com.metropolitan.FamilyFridge.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping ("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @Autowired
    private GroceryService groceryService;
    @Autowired
    private MealIngredientRepository mealIngredientRepository;

    Meal meal = new Meal();

    @GetMapping()
    public String meals(Model model) {
        meal = new Meal();
        model.addAttribute("meals", mealService.getAllMeals());
        return "meals";
    }

    @GetMapping("/add")
    public String addMeal(Model model) {

        if (meal.getMealIngredients() == null) {
            meal.setMealIngredients(new ArrayList<>());
        }
        model.addAttribute("meal", meal);
        model.addAttribute("groceries", groceryService.getAll());
        return "add_meal";
    }

    @PostMapping("/removeIngredient")
    public String removeIngredient(@RequestParam("ingredientIndex") int ingredientIndex, Model model) {

        MealIngredient mealIngredient = meal.getMealIngredients().get(ingredientIndex);

//        if (mealIngredient.getId() != null) {
//            mealIngredientRepository.delete(mealIngredient);
//        }

        meal.getMealIngredients().remove(mealIngredient);
        model.addAttribute("meal", meal);
        model.addAttribute("groceries", groceryService.getAll());
        return "add_meal";
    }

    @GetMapping("/edit/{id}")
    public String editMeal(@PathVariable("id") Long mealId, Model model) {

        meal = mealService.getMealById(mealId);
        if (meal == null){
            return "error";
        }
        model.addAttribute("meal", meal);
        model.addAttribute("groceries", groceryService.getAll());
        return "add_meal";
    }

    @PostMapping("/addIngredient")
    public String addIngredient(@RequestParam("grocery") Long groceryId, @RequestParam("quantity") Double quantity, Model model) {

        Grocery grocery = groceryService.getById(groceryId);
        if (grocery == null) {
            return "error";
        }

        MealIngredient mealIngredient = new MealIngredient();
        mealIngredient.setGrocery(grocery);
        mealIngredient.setQuantity(quantity);
        mealIngredient.setMeal(meal);

        if (meal.getMealIngredients() == null) {
            meal.setMealIngredients(new ArrayList<>());
        }

        meal.addMealIngredient(mealIngredient);

        model.addAttribute("meal", meal);
        model.addAttribute("groceries", groceryService.getAll());
        return "add_meal";
    }

    @PostMapping("/addMeal")
    public String addNewMeal(@RequestParam("name") String name,Model model) {

        meal.setName(name);
        mealService.save(meal);

        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            if (mealIngredient.getMeal() == null) {
                mealIngredient.setMeal(meal);
                mealIngredientRepository.save(mealIngredient);
            }
        }

        meal = new Meal();
        return "redirect:/meals";
    }
}
