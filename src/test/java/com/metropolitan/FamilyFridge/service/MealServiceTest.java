package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.repository.MealIngredientRepository;
import com.metropolitan.FamilyFridge.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private MealIngredientRepository mealIngredientRepository;

    @InjectMocks
    private MealService mealService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMeals() {
        // Prepare test data
        Meal meal1 = new Meal();
        Meal meal2 = new Meal();
        List<Meal> meals = new ArrayList<>();
        meals.add(meal1);
        meals.add(meal2);

        // Define mock behavior
        when(mealRepository.findAll()).thenReturn(meals);

        // Call the method to test
        List<Meal> result = mealService.getAllMeals();

        // Verify results
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(meal1, meal2);
        verify(mealRepository).findAll();
    }

    @Test
    void testGetMealById() {
        // Prepare test data
        Long mealId = 1L;
        Meal meal = new Meal();

        // Define mock behavior
        when(mealRepository.findById(mealId)).thenReturn(Optional.of(meal));

        // Call the method to test
        Meal result = mealService.getMealById(mealId);

        // Verify results
        assertThat(result).isEqualTo(meal);
        verify(mealRepository).findById(mealId);
    }

    @Test
    void testSaveMeal() {
        // Prepare test data
        Meal meal = new Meal();

        // Call the method to test
        mealService.save(meal);

        // Verify results
        verify(mealRepository).save(meal);
    }
}
