package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    public Map<Date, List<MealPlan>> getMealPlansByDate(Date startDate, Date endDate) {
        List<MealPlan> mealPlans = mealPlanRepository.findMealPlanBetweenDates(startDate, endDate);
        return mealPlans.stream().collect(Collectors.groupingBy(MealPlan::getDate));
    }

    public Map<String, List<MealPlan>> getMealPlansByDate(Date date) {
        List<MealPlan> mealPlans = mealPlanRepository.findAllByDate(date);
        Map<String, List<MealPlan>> mealPlansByDate = mealPlans.stream().collect(Collectors.groupingBy(mealPlan -> mealPlan.getTimeOfDay().getName()));
        return mealPlansByDate;
    }

}
