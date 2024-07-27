package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Inventory;
import com.metropolitan.FamilyFridge.service.GroceryService;
import com.metropolitan.FamilyFridge.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryControllerTest {

    private InventoryController inventoryController;
    private InventoryService mockInventoryService;
    private GroceryService mockGroceryService;
    private Model model;

    @BeforeEach
    void setUp() {
        mockInventoryService = mock(InventoryService.class);
        mockGroceryService = mock(GroceryService.class);
        inventoryController = new InventoryController();
        inventoryController.inventoryService = mockInventoryService;
        inventoryController.groceryService = mockGroceryService;
        model = mock(Model.class);
    }

    @Test
    void testInventory() {
        List<Inventory> inventoryList = List.of(new Inventory(), new Inventory());
        List<Grocery> groceryList = List.of(new Grocery(), new Grocery());
        when(mockInventoryService.getAll()).thenReturn(inventoryList);
        when(mockGroceryService.getAll()).thenReturn(groceryList);

        String viewName = inventoryController.inventory(model);

        assertThat(viewName).isEqualTo("inventory");
        verify(model).addAttribute("inventory", inventoryList);
        verify(model).addAttribute("groceries", groceryList);
    }

    @Test
    void testAddInventory_NewGrocery() {
        Grocery grocery = new Grocery();
        grocery.setId(1L);
        when(mockGroceryService.getById(1L)).thenReturn(grocery);
        when(mockInventoryService.getByGrocery(grocery)).thenReturn(null);

        String viewName = inventoryController.add(1L, 5.0);

        assertThat(viewName).isEqualTo("redirect:/inventory");
        verify(mockGroceryService).getById(1L);
        verify(mockInventoryService).getByGrocery(grocery);
        verify(mockInventoryService).save(any(Inventory.class));
    }

    @Test
    void testAddInventory_ExistingGrocery() {
        Grocery grocery = new Grocery();
        grocery.setId(1L);
        Inventory existingInventory = new Inventory();
        existingInventory.setGrocery(grocery);
        existingInventory.setQuantity(10.0);
        when(mockGroceryService.getById(1L)).thenReturn(grocery);
        when(mockInventoryService.getByGrocery(grocery)).thenReturn(existingInventory);

        String viewName = inventoryController.add(1L, 5.0);

        assertThat(viewName).isEqualTo("redirect:/inventory");
        verify(mockGroceryService).getById(1L);
        verify(mockInventoryService).getByGrocery(grocery);
        assertThat(existingInventory.getQuantity()).isEqualTo(15.0);
        verify(mockInventoryService).save(existingInventory);
    }

    @Test
    void testAddInventory_GroceryNotFound() {
        when(mockGroceryService.getById(1L)).thenReturn(null);

        String viewName = inventoryController.add(1L, 5.0);

        assertThat(viewName).isEqualTo("error");
        verify(mockGroceryService).getById(1L);
        verify(mockInventoryService, never()).save(any(Inventory.class));
    }

    @Test
    void testRemoveInventory() {
        String viewName = inventoryController.remove(1L);

        assertThat(viewName).isEqualTo("redirect:/inventory");
        verify(mockInventoryService).deleteById(1L);
    }
}
