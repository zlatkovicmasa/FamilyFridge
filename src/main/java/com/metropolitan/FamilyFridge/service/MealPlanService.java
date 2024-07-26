package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.repository.MealPlanRepository;
import com.metropolitan.FamilyFridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private UserRepository userRepository;

    public List<MealPlan> getMealPlansBetweenDates(Date startDate, Date endDate, Boolean onlyAcctepted) {
        List<MealPlan> mealPlans = mealPlanRepository.findMealPlanBetweenDates(startDate, endDate);

        if (onlyAcctepted) {
            return mealPlans.stream()
                    .filter(MealPlan::getAccepted)
                    .collect(Collectors.toList());
        }
        return mealPlans;
    }

    public Map<String, List<MealPlan>> getMealPlansByDate(Date date) {
        List<MealPlan> mealPlans = mealPlanRepository.findAllByDate(date);
        return mealPlans.stream().collect(Collectors.groupingBy(mealPlan -> mealPlan.getTimeOfDay().getName()));
    }

    public MealPlan getMealPlanById(Long id) {
        return mealPlanRepository.findById(id).orElse(null);
    }

    public void save(MealPlan mealPlan) {
        if (mealPlan.getSuggestedBy() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    String username = ((UserDetails) principal).getUsername();
                    FamilyUser currentUser = userRepository.findByEmail(username).orElse(null);
                    mealPlan.setSuggestedBy(currentUser);
                }
            }
        }
        mealPlanRepository.save(mealPlan);
    }
}
