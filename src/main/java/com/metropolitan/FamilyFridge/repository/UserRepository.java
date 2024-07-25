package com.metropolitan.FamilyFridge.repository;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<FamilyUser, Long> {

    Optional<FamilyUser> findByEmail(String email);
}
