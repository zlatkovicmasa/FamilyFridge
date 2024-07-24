package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.TimeOfDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeOfDayRepository extends JpaRepository<TimeOfDay, Long> {
}
