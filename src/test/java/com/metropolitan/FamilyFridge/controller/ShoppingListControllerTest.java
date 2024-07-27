package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.service.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListControllerTest {

    private ShoppingListController shoppingListController;
    private ShoppingListService mockShoppingListService;
    private Model model;

    @BeforeEach
    public void setUp() {
        mockShoppingListService = mock(ShoppingListService.class);
        shoppingListController = new ShoppingListController();
        shoppingListController.shoppingListService = mockShoppingListService;
        model = mock(Model.class);
    }

    @Test
    void testGetShoppingListWithDates() {
        LocalDate start = LocalDate.of(2024, 7, 22); // Monday
        LocalDate end = LocalDate.of(2024, 7, 28); // Sunday
        Date startDate = Date.valueOf(start);
        Date endDate = Date.valueOf(end);

        Map<Grocery, Double> shoppingList = Map.of(new Grocery(), 10.0);
        when(mockShoppingListService.generateShoppingList(startDate, endDate)).thenReturn(shoppingList);

        String viewName = shoppingListController.getShoppingList(startDate, endDate, model);

        assertThat(viewName).isEqualTo("shopping_list");
        verify(model).addAttribute("shoppingList", shoppingList);
        verify(model).addAttribute("startDate", startDate);
        verify(model).addAttribute("endDate", endDate);
    }

    @Test
    void testGetShoppingListWithoutDates() {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(DayOfWeek.MONDAY);
        LocalDate end = now.with(DayOfWeek.SUNDAY);
        Date startDate = Date.valueOf(start);
        Date endDate = Date.valueOf(end);

        Map<Grocery, Double> shoppingList = Map.of(new Grocery(), 15.0);
        when(mockShoppingListService.generateShoppingList(startDate, endDate)).thenReturn(shoppingList);

        String viewName = shoppingListController.getShoppingList(null, null, model);

        assertThat(viewName).isEqualTo("shopping_list");
        verify(model).addAttribute("shoppingList", shoppingList);
        verify(model).addAttribute("startDate", startDate);
        verify(model).addAttribute("endDate", endDate);
    }
}
