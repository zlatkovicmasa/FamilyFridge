package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Inventory;
import com.metropolitan.FamilyFridge.service.GroceryService;
import com.metropolitan.FamilyFridge.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private GroceryService groceryService;

    @GetMapping("")
    public String inventory(Model model) {
        List<Inventory> inventoryList = inventoryService.getAll();
        model.addAttribute("inventory", inventoryList);
        model.addAttribute("groceries", groceryService.getAll());
        return "inventory";
    }

    @PostMapping("/add")
    public String add(@RequestParam("grocery") Long groceryId, @RequestParam("quantity") double quantity) {

        Grocery grocery = groceryService.getById(groceryId);
        if (grocery == null) {
            return "error";
        }

        Inventory existingInventory = inventoryService.getByGrocery(grocery);

        if (existingInventory != null) {
            existingInventory.setQuantity(existingInventory.getQuantity() + quantity);
            inventoryService.save(existingInventory);
        } else {
            // Ako ne postoji, dodaj novi unos
            Inventory inventory = new Inventory();
            inventory.setGrocery(grocery);
            inventory.setQuantity(quantity);
            inventoryService.save(inventory);
        }

        return "redirect:/inventory";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("groceryId") Long groceryId) {

        inventoryService.deleteById(groceryId);

        return "redirect:/inventory";
    }
}
