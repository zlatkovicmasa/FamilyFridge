package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.entity.RegistrationCommand;
import com.metropolitan.FamilyFridge.exception.RegistrationFailedException;
import com.metropolitan.FamilyFridge.repository.UserRepository;
import com.metropolitan.FamilyFridge.security.FamilyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FamilyUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<FamilyUser> findAll() {
        return userRepository.findAll();
    }

    public void register(FamilyUser familyUser) {
        familyUser.setPassword(passwordEncoder.encode(familyUser.getPassword()));
        userRepository.save(familyUser);
    }

    public UserDetails register(RegistrationCommand command) throws RegistrationFailedException {
        String username = command.getUsername();
        String password = command.getPassword();
        String repeatPassword = command.getRepeatPassword();
        boolean passwordsMatch = Objects.equals(password, repeatPassword);
        boolean userExists = userRepository.findByEmail(username).isPresent();

        if (!passwordsMatch) {
            throw new RegistrationFailedException("Passwords don't match!");
        }
        else if (userExists) {
            throw new RegistrationFailedException("User already exists!");
        }
        else {
            FamilyUser newUser = new FamilyUser(username, passwordEncoder.encode(password), "ROLE_ADMIN");
            FamilyUser savedUser = userRepository.save(newUser);
            return new FamilyUserDetails(savedUser);
        }

    }
}
