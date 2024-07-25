package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.Grocery;
import com.metropolitan.FamilyFridge.entity.Inventory;
import com.metropolitan.FamilyFridge.repository.GroceryRepository;
import com.metropolitan.FamilyFridge.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    public Inventory getById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public void save(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Inventory getByGrocery(Grocery grocery) {
        return inventoryRepository.findByGrocery(grocery);
    }
}
