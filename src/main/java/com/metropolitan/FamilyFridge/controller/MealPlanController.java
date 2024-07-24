package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.entity.TimeOfDay;
import com.metropolitan.FamilyFridge.model.MealPlanData;
import com.metropolitan.FamilyFridge.repository.MealPlanRepository;
import com.metropolitan.FamilyFridge.repository.TimeOfDayRepository;
import com.metropolitan.FamilyFridge.service.MealPlanService;
import com.metropolitan.FamilyFridge.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    @Autowired
    private MealService mealService;

    @Autowired
    private TimeOfDayRepository timeOfDayRepository;

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

    @PostMapping("/mealPlan/add")
    public String addMealPlan(@RequestParam("date") Date date, @RequestParam("timeOfDayId") Long timeOfDayId, Model model) {

        MealPlan mealPlan = new MealPlan();
        mealPlan.setDate(date);

        TimeOfDay timeOfDay = timeOfDayRepository.findById(timeOfDayId).orElse(null);
        if (timeOfDay == null) {
            return "error";
        }

        mealPlan.setTimeOfDay(timeOfDay);

        model.addAttribute("mealPlan", mealPlan);
        model.addAttribute("meals", mealService.getAllMeals());
        return "meals";
    }

    @PostMapping("/mealPlan/addMeal")
    public String addMealToPlan(@ModelAttribute("mealPlan") MealPlan mealPlan,
                                @RequestParam("mealId") Long mealId,
                                @RequestParam("timeOfDayId") Long timeOfDayId,
                                @RequestParam("date") Date date,
                                Model model) {

        Meal meal = mealService.getMealById(mealId);
        if (meal == null) {
            return "error";
        }
        mealPlan.setTimeOfDay(timeOfDayRepository.findById(timeOfDayId).orElse(null));
        mealPlan.setMeal(meal);
        mealPlan.setDate(date);
        mealPlanService.save(mealPlan);
        return "redirect:/home";
    }
}
