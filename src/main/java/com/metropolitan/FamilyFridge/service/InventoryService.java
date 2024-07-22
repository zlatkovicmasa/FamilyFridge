package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
}
