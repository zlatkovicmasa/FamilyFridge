package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MealPlanController {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @GetMapping("/home")
    public String home(Model model) {
        List<MealPlan> mealPlans = mealPlanRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        return "home";
    }
}
