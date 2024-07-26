package com.metropolitan.FamilyFridge.security;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FamilyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FamilyUser user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new FamilyUserDetails(user);
    }
}