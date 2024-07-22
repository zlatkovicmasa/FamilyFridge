package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
