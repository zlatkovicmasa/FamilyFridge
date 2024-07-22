package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.repository.GroceryRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groceries")
public class GroceryController {

    @Autowired
    private GroceryRepository groceryRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("groceries", groceryRepository.findAll());
        return "groceries";
    }
}
