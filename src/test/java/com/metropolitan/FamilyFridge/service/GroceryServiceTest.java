package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.repository.GroceryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GroceryServiceTest {

    @InjectMocks
    private GroceryService groceryService;

    @Mock
    private GroceryRepository groceryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Grocery grocery1 = new Grocery();
        Grocery grocery2 = new Grocery();
        List<Grocery> groceries = List.of(grocery1, grocery2);

        when(groceryRepository.findAll()).thenReturn(groceries);

        List<Grocery> result = groceryService.getAll();

        assertThat(result).isEqualTo(groceries);
        verify(groceryRepository).findAll();
    }

    @Test
    void testGetById_Found() {
        Long id = 1L;
        Grocery grocery = new Grocery();

        when(groceryRepository.findById(id)).thenReturn(Optional.of(grocery));

        Grocery result = groceryService.getById(id);

        assertThat(result).isEqualTo(grocery);
        verify(groceryRepository).findById(id);
    }

    @Test
    void testGetById_NotFound() {
        Long id = 1L;

        when(groceryRepository.findById(id)).thenReturn(Optional.empty());

        Grocery result = groceryService.getById(id);

        assertThat(result).isNull();
        verify(groceryRepository).findById(id);
    }
}
