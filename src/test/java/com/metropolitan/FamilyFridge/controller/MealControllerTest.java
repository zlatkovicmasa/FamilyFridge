package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealIngredient;
import com.metropolitan.FamilyFridge.repository.MealIngredientRepository;
import com.metropolitan.FamilyFridge.service.GroceryService;
import com.metropolitan.FamilyFridge.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MealControllerTest {

    private MealController mealController;
    private MealService mockMealService;
    private GroceryService mockGroceryService;
    private MealIngredientRepository mockMealIngredientRepository;
    private Model model;

    @BeforeEach
    public void setUp() {
        mockMealService = mock(MealService.class);
        mockGroceryService = mock(GroceryService.class);
        mockMealIngredientRepository = mock(MealIngredientRepository.class);
        mealController = new MealController();
        mealController.mealService = mockMealService;
        mealController.groceryService = mockGroceryService;
        mealController.mealIngredientRepository = mockMealIngredientRepository;
        model = mock(Model.class);
    }

    @Test
    void testMeals() {
        List<Meal> meals = List.of(new Meal(), new Meal());
        when(mockMealService.getAllMeals()).thenReturn(meals);

        String viewName = mealController.meals(model);

        assertThat(viewName).isEqualTo("meals");
        verify(model).addAttribute("meals", meals);
    }

    @Test
    void testAddMeal() {
        List<Grocery> groceries = List.of(new Grocery(), new Grocery());
        when(mockGroceryService.getAll()).thenReturn(groceries);

        String viewName = mealController.addMeal(model);

        assertThat(viewName).isEqualTo("add_meal");
        verify(model).addAttribute(eq("meal"), any(Meal.class));
        verify(model).addAttribute("groceries", groceries);
    }

    @Test
    void testRemoveIngredient() {
        Meal meal = new Meal();
        MealIngredient mealIngredient = new MealIngredient();
        meal.setMealIngredients(List.of(mealIngredient));
        mealController.meal = meal;

        String viewName = mealController.removeIngredient(0, model);

        assertThat(viewName).isEqualTo("add_meal");
        assertThat(meal.getMealIngredients()).isEmpty();
        verify(model).addAttribute("meal", meal);
        verify(model).addAttribute(eq("groceries"), any(List.class));
    }

    @Test
    void testEditMeal() {
        Meal meal = new Meal();
        when(mockMealService.getMealById(1L)).thenReturn(meal);
        List<Grocery> groceries = List.of(new Grocery(), new Grocery());
        when(mockGroceryService.getAll()).thenReturn(groceries);

        String viewName = mealController.editMeal(1L, model);

        assertThat(viewName).isEqualTo("add_meal");
        verify(model).addAttribute("meal", meal);
        verify(model).addAttribute("groceries", groceries);
    }

    @Test
    void testEditMealNotFound() {
        when(mockMealService.getMealById(1L)).thenReturn(null);

        String viewName = mealController.editMeal(1L, model);

        assertThat(viewName).isEqualTo("error");
    }

    @Test
    void testAddIngredient() {
        Grocery grocery = new Grocery();
        when(mockGroceryService.getById(1L)).thenReturn(grocery);
        Meal meal = new Meal();
        mealController.meal = meal;

        String viewName = mealController.addIngredient(1L, 5.0, model);

        assertThat(viewName).isEqualTo("add_meal");
        assertThat(meal.getMealIngredients()).hasSize(1);
        verify(model).addAttribute("meal", meal);
        verify(model).addAttribute(eq("groceries"), any(List.class));
    }

    @Test
    void testAddIngredientGroceryNotFound() {
        when(mockGroceryService.getById(1L)).thenReturn(null);

        String viewName = mealController.addIngredient(1L, 5.0, model);

        assertThat(viewName).isEqualTo("error");
    }

    @Test
    void testAddNewMeal() {
        Meal meal = new Meal();
        mealController.meal = meal;
        meal.setMealIngredients(List.of(new MealIngredient()));

        String viewName = mealController.addNewMeal("New Meal", model);

        assertThat(viewName).isEqualTo("redirect:/meals");
        assertThat(meal.getName()).isEqualTo("New Meal");
        verify(mockMealService).save(meal);
        verify(mockMealIngredientRepository, atLeastOnce()).save(any(MealIngredient.class));
    }
}
