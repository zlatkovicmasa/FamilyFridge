package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/shoppingList")
public class ShoppingListController {

    @Autowired
    ShoppingListService shoppingListService;

    @GetMapping
    public String getShoppingList(@RequestParam(value = "startDate", required = false) Date startDate,
                                  @RequestParam(value = "endDate", required = false) Date endDate, Model model) {

        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            startDate = Date.valueOf(now.with(DayOfWeek.MONDAY));
            endDate = Date.valueOf(now.with(DayOfWeek.SUNDAY));
        }

        Map<Grocery, Double> shoppingList = shoppingListService.generateShoppingList(startDate, endDate);
        model.addAttribute("shoppingList", shoppingList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "shopping_list";
    }
}
