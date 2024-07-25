package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Inventory;
import com.metropolitan.FamilyFridge.entity.MealIngredient;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingListService {

    @Autowired
    MealPlanService mealPlanService;

    @Autowired
    InventoryService inventoryService;

    public Map<Grocery, Double> generateShoppingList(Date startDate, Date endDate) {

        Map<Grocery, Double> shoppingList = new HashMap<>();

        List<MealPlan> mealPlans = mealPlanService.getMealPlansBetweenDates(startDate, endDate);

        Map<Grocery, Double> neededGroceries = new HashMap<>();

        for (MealPlan mealPlan : mealPlans) {
            for (MealIngredient ingredient : mealPlan.getMeal().getMealIngredients()) {
                neededGroceries.merge(ingredient.getGrocery(), ingredient.getQuantity(), Double::sum);
            }
        }

        List<Inventory> inventories = inventoryService.getAll();
        for (Map.Entry<Grocery, Double> entry : neededGroceries.entrySet()) {
            Grocery grocery = entry.getKey();
            Double neededQuantity = entry.getValue();

            Inventory inventory = inventories.stream()
                    .filter(inv -> inv.getGrocery().equals(grocery))
                    .findFirst()
                    .orElse(null);

            double availableQuantity = (inventory != null) ? inventory.getQuantity() : 0.0;

            if (availableQuantity < neededQuantity) {
                shoppingList.put(grocery, neededQuantity - availableQuantity);
            }
        }
        return shoppingList;
    }
}
