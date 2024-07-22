package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
