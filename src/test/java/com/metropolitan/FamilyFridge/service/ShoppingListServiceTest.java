package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListServiceTest {

    @Mock
    private MealPlanService mealPlanService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ShoppingListService shoppingListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateShoppingList() {
        // Prepare test data
        Grocery grocery1 = new Grocery("namirnica1");
        grocery1.setId(1L);
        Grocery grocery2 = new Grocery("namirnica2");
        grocery2.setId(2L);

        MealIngredient ingredient1 = new MealIngredient();
        ingredient1.setGrocery(grocery1);
        ingredient1.setQuantity(3.0);

        MealIngredient ingredient2 = new MealIngredient();
        ingredient2.setGrocery(grocery2);
        ingredient2.setQuantity(2.0);

        Meal meal = new Meal();
        meal.setMealIngredients(Arrays.asList(ingredient1, ingredient2));

        MealPlan mealPlan = new MealPlan();
        mealPlan.setMeal(meal);

        List<MealPlan> mealPlans = Collections.singletonList(mealPlan);

        Inventory inventory1 = new Inventory();
        inventory1.setGrocery(grocery1);
        inventory1.setQuantity(1.0);

        Inventory inventory2 = new Inventory();
        inventory2.setGrocery(grocery2);
        inventory2.setQuantity(2.0);

        List<Inventory> inventories = Arrays.asList(inventory1, inventory2);

        Date startDate = Date.valueOf("2024-07-01");
        Date endDate = Date.valueOf("2024-07-07");

        // Define mock behavior
        when(mealPlanService.getMealPlansBetweenDates(startDate, endDate, true)).thenReturn(mealPlans);
        when(inventoryService.getAll()).thenReturn(inventories);

        // Expected results
        Map<Grocery, Double> expectedShoppingList = new HashMap<>();
        expectedShoppingList.put(grocery1, 2.0); // 3.0 needed - 1.0 available// 2.0 needed - 2.0 available

        // Call the method to test
        Map<Grocery, Double> result = shoppingListService.generateShoppingList(startDate, endDate);

        // Verify results
        assertThat(result).hasSize(expectedShoppingList.size()); // Only grocery1 should be in the shopping list
        assertThat(result).containsExactlyEntriesOf(expectedShoppingList);
        verify(mealPlanService).getMealPlansBetweenDates(startDate, endDate, true);
        verify(inventoryService).getAll();
    }
}
