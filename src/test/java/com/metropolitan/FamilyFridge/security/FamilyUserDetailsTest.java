package com.metropolitan.FamilyFridge.security;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FamilyUserDetailsTest {

    private FamilyUserDetails userDetails;
    private FamilyUserDetails adminDetails;

    private List<SimpleGrantedAuthority> expectedRole;

    @BeforeEach
    void setUp() {
        adminDetails = new FamilyUserDetails(new FamilyUser("admin@email.com", "password", "ROLE_ADMIN"));
        userDetails = new FamilyUserDetails(new FamilyUser("user@email.com", "password", "ROLE_USER"));
    }

    @Test
    void testGetAuthorities_WhenUserIsAdmin() {
        List<SimpleGrantedAuthority> expectedRole = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        assertThat(adminDetails.getAuthorities()).isEqualTo(expectedRole);
    }

    @Test
    void testGetAuthorities_WhenUserIsNotAdmin() {
        List<SimpleGrantedAuthority> expectedRole = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        assertThat(userDetails.getAuthorities()).isEqualTo(expectedRole);
    }
}
