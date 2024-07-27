package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Inventory;
import com.metropolitan.FamilyFridge.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Inventory inventory1 = new Inventory();
        Inventory inventory2 = new Inventory();
        List<Inventory> inventories = List.of(inventory1, inventory2);

        when(inventoryRepository.findAll()).thenReturn(inventories);

        List<Inventory> result = inventoryService.getAll();

        assertThat(result).isEqualTo(inventories);
        verify(inventoryRepository).findAll();
    }

    @Test
    void testSave() {
        Inventory inventory = new Inventory();

        inventoryService.save(inventory);

        verify(inventoryRepository).save(inventory);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        inventoryService.deleteById(id);

        verify(inventoryRepository).deleteById(id);
    }

    @Test
    void testGetByGrocery_Found() {
        Grocery grocery = new Grocery();
        Inventory inventory = new Inventory();

        when(inventoryRepository.findByGrocery(grocery)).thenReturn(inventory);

        Inventory result = inventoryService.getByGrocery(grocery);

        assertThat(result).isEqualTo(inventory);
        verify(inventoryRepository).findByGrocery(grocery);
    }

    @Test
    void testGetByGrocery_NotFound() {
        Grocery grocery = new Grocery();

        when(inventoryRepository.findByGrocery(grocery)).thenReturn(null);

        Inventory result = inventoryService.getByGrocery(grocery);

        assertThat(result).isNull();
        verify(inventoryRepository).findByGrocery(grocery);
    }
}
