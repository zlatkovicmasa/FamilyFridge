package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.entity.MealPlan;
import com.metropolitan.FamilyFridge.entity.TimeOfDay;
import com.metropolitan.FamilyFridge.repository.MealPlanRepository;
import com.metropolitan.FamilyFridge.repository.UserRepository;
import com.metropolitan.FamilyFridge.security.FamilyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MealPlanServiceTest {

    @InjectMocks
    private MealPlanService mealPlanService;

    @Mock
    private MealPlanRepository mealPlanRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMealPlansBetweenDates() {
        Date startDate = Date.valueOf("2024-01-01");
        Date endDate = Date.valueOf("2024-01-31");
        Boolean onlyAccepted = true;
        MealPlan mealPlan1 = new MealPlan();
        mealPlan1.setAccepted(true);
        MealPlan mealPlan2 = new MealPlan();
        mealPlan2.setAccepted(false);
        List<MealPlan> mealPlans = List.of(mealPlan1, mealPlan2);

        when(mealPlanRepository.findMealPlanBetweenDates(startDate, endDate)).thenReturn(mealPlans);

        List<MealPlan> result = mealPlanService.getMealPlansBetweenDates(startDate, endDate, onlyAccepted);

        assertThat(result).containsExactly(mealPlan1);
        verify(mealPlanRepository).findMealPlanBetweenDates(startDate, endDate);
    }

    @Test
    void testGetMealPlansByDate() {
        Date date = Date.valueOf("2024-01-01");
        MealPlan mealPlan = new MealPlan();
        mealPlan.setTimeOfDay(new TimeOfDay("Lunch"));
        List<MealPlan> mealPlans = List.of(mealPlan);

        when(mealPlanRepository.findAllByDate(date)).thenReturn(mealPlans);

        Map<String, List<MealPlan>> result = mealPlanService.getMealPlansByDate(date);

        assertThat(result).containsKey("Lunch");
        assertThat(result.get("Lunch")).containsExactly(mealPlan);
        verify(mealPlanRepository).findAllByDate(date);
    }

    @Test
    void testGetMealPlanById() {
        Long id = 1L;
        MealPlan mealPlan = new MealPlan();

        when(mealPlanRepository.findById(id)).thenReturn(Optional.of(mealPlan));

        MealPlan result = mealPlanService.getMealPlanById(id);

        assertThat(result).isEqualTo(mealPlan);
        verify(mealPlanRepository).findById(id);
    }

    @Test
    void testSaveWithAuthentication() {
        MealPlan mealPlan = new MealPlan();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        FamilyUserDetails familyUserDetails = mock(FamilyUserDetails.class);

        FamilyUser newUser = new FamilyUser("test@email.com", "password", "ROLE_ADMIN");

        SecurityContextHolder.clearContext();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(familyUserDetails);
        when(familyUserDetails.getUsername()).thenReturn("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(newUser));

        mealPlanService.save(mealPlan);

        assertThat(mealPlan.getSuggestedBy()).isEqualTo(newUser);
        verify(mealPlanRepository).save(mealPlan);
    }

    @Test
    void testSaveWithoutAuthentication() {
        MealPlan mealPlan = new MealPlan();

        SecurityContextHolder.clearContext();

        mealPlanService.save(mealPlan);

        assertThat(mealPlan.getSuggestedBy()).isNull();
        verify(mealPlanRepository).save(mealPlan);
    }
}
