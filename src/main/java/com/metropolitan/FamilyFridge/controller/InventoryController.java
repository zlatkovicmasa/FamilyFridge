package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Inventory;
import com.metropolitan.FamilyFridge.repository.GroceryRepository;
import com.metropolitan.FamilyFridge.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private GroceryRepository groceryRepository;

    @GetMapping("")
    public String inventory(Model model) {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        model.addAttribute("inventory", inventoryRepository.findAll());
        return "inventory";
    }

    @GetMapping("/add")
    public String addInventory(Model model) {
        model.addAttribute("inventory", new Inventory());
        List<Grocery> groceryList = groceryRepository.findAll();
        model.addAttribute("groceries", groceryList);
        return "add_inventory";
    }

    @PostMapping("/add")
    public String addInventory(@ModelAttribute("inventory") Inventory inventory) {
        inventoryRepository.save(inventory);
        return "redirect:/inventory";
    }

    @PostMapping("/update/{id}")
    public String updateInventory(@PathVariable("id") Long id, @RequestParam("quantityChange") Model model) {
        //inventoryRepository.
        return "redirect:/inventory/";
    }
}
