package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.repository.MealPlanRepository;
import com.metropolitan.FamilyFridge.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    @GetMapping("/home")
    public String home(@RequestParam(name = "date", required = false) String dateString, Model model) {

        LocalDate date;
        if (dateString == null || dateString.isEmpty()) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(dateString);
        }

        Date selectedDate = Date.valueOf(date);
        Map<String, List<MealPlan>> mealPlans = mealPlanService.getMealPlansByDate(selectedDate);


       // Map<Date, List<MealPlan>> mealPlans = mealPlanService.getMealPlansByDate(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(14)));
        model.addAttribute("mealPlans", mealPlans);
        model.addAttribute("date", selectedDate);
        return "home";
    }
}
