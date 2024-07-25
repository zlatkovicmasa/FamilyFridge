package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    public List<MealPlan> getMealPlansBetweenDates(Date startDate, Date endDate) {
        return mealPlanRepository.findMealPlanBetweenDates(startDate, endDate);
    }

    public Map<String, List<MealPlan>> getMealPlansByDate(Date date) {
        List<MealPlan> mealPlans = mealPlanRepository.findAllByDate(date);
        return mealPlans.stream().collect(Collectors.groupingBy(mealPlan -> mealPlan.getTimeOfDay().getName()));
    }

    public void save(MealPlan mealPlan) {
        mealPlanRepository.save(mealPlan);
    }

    public List<MealPlan> getAll(){
        return mealPlanRepository.findAll();
    }

}
