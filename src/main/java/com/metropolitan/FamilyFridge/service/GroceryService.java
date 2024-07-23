package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryService {

    @Autowired
    private GroceryRepository groceryRepository;

    public List<Grocery> getAll() {
        return groceryRepository.findAll();
    }

    public Grocery getById(Long id) {
        return groceryRepository.findById(id).orElse(null);
    }
}
