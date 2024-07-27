package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Meal;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.entity.TimeOfDay;
import com.metropolitan.FamilyFridge.repository.TimeOfDayRepository;
import com.metropolitan.FamilyFridge.service.MealPlanService;
import com.metropolitan.FamilyFridge.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MealPlanControllerTest {

    private MealPlanController mealPlanController;
    private MealPlanService mockMealPlanService;
    private MealService mockMealService;
    private TimeOfDayRepository mockTimeOfDayRepository;
    private Model model;

    @BeforeEach
    public void setUp() {
        mockMealPlanService = mock(MealPlanService.class);
        mockMealService = mock(MealService.class);
        mockTimeOfDayRepository = mock(TimeOfDayRepository.class);
        mealPlanController = new MealPlanController();
        mealPlanController.mealPlanService = mockMealPlanService;
        mealPlanController.mealService = mockMealService;
        mealPlanController.timeOfDayRepository = mockTimeOfDayRepository;
        model = mock(Model.class);
    }

    @Test
    void testHome() {
        LocalDate now = LocalDate.now();
        Date date = Date.valueOf(now);
        Map<String, List<MealPlan>> mealPlans = Map.of("Breakfast", List.of(new MealPlan()));
        when(mockMealPlanService.getMealPlansByDate(date)).thenReturn(mealPlans);

        String viewName = mealPlanController.home(null, model);

        assertThat(viewName).isEqualTo("home");
        verify(model).addAttribute("mealPlans", mealPlans);
        verify(model).addAttribute("date", date);
    }

    @Test
    void testAddMealPlan() {
        Date date = Date.valueOf(LocalDate.now());
        TimeOfDay timeOfDay = new TimeOfDay();
        when(mockTimeOfDayRepository.findById(1L)).thenReturn(Optional.of(timeOfDay));
        List<Meal> meals = List.of(new Meal());
        when(mockMealService.getAllMeals()).thenReturn(meals);

        String viewName = mealPlanController.addMealPlan(date, 1L, model);

        assertThat(viewName).isEqualTo("meals");
        verify(model).addAttribute(eq("mealPlan"), any(MealPlan.class));
        verify(model).addAttribute("meals", meals);
    }

    @Test
    void testAddMealPlanTimeOfDayNotFound() {
        Date date = Date.valueOf(LocalDate.now());
        when(mockTimeOfDayRepository.findById(1L)).thenReturn(Optional.empty());

        String viewName = mealPlanController.addMealPlan(date, 1L, model);

        assertThat(viewName).isEqualTo("error");
    }

    @Test
    void testAddMealToPlan() {
        Meal meal = new Meal();
        when(mockMealService.getMealById(1L)).thenReturn(meal);
        TimeOfDay timeOfDay = new TimeOfDay();
        when(mockTimeOfDayRepository.findById(1L)).thenReturn(Optional.of(timeOfDay));
        MealPlan mealPlan = new MealPlan();

        String viewName = mealPlanController.addMealToPlan(mealPlan, 1L, 1L, Date.valueOf(LocalDate.now()), model);

        assertThat(viewName).isEqualTo("redirect:/home");
        assertThat(mealPlan.getMeal()).isEqualTo(meal);
        assertThat(mealPlan.getTimeOfDay()).isEqualTo(timeOfDay);
        verify(mockMealPlanService).save(mealPlan);
    }

    @Test
    void testAddMealToPlanMealNotFound() {
        when(mockMealService.getMealById(1L)).thenReturn(null);
        MealPlan mealPlan = new MealPlan();

        String viewName = mealPlanController.addMealToPlan(mealPlan, 1L, 1L, Date.valueOf(LocalDate.now()), model);

        assertThat(viewName).isEqualTo("error");
    }

    @Test
    void testAcceptMealPlan() {
        MealPlan mealPlan = new MealPlan();
        when(mockMealPlanService.getMealPlanById(1L)).thenReturn(mealPlan);

        String viewName = mealPlanController.acceptMealPlan(1L, model);

        assertThat(viewName).isEqualTo("redirect:/home");
        assertThat(mealPlan.getAccepted()).isTrue();
        verify(mockMealPlanService).save(mealPlan);
    }

    @Test
    void testRejectMealPlan() {
        MealPlan mealPlan = new MealPlan();
        when(mockMealPlanService.getMealPlanById(1L)).thenReturn(mealPlan);

        String viewName = mealPlanController.rejectMealPlan(1L, model);

        assertThat(viewName).isEqualTo("redirect:/home");
        assertThat(mealPlan.getAccepted()).isFalse();
        verify(mockMealPlanService).save(mealPlan);
    }
}
