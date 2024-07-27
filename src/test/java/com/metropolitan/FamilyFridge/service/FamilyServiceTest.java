package com.metropolitan.FamilyFridge.service;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.entity.RegistrationCommand;
import com.metropolitan.FamilyFridge.exception.RegistrationFailedException;
import com.metropolitan.FamilyFridge.repository.UserRepository;
import com.metropolitan.FamilyFridge.security.FamilyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FamilyServiceTest {

    @InjectMocks
    private FamilyUserService familyUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {

        FamilyUser user1 = new FamilyUser();
        FamilyUser user2 = new FamilyUser();
        List<FamilyUser> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<FamilyUser> result = familyUserService.findAll();

        assertThat(result).isEqualTo(users);
    }

    @Test
    void testRegisterFamilyUser() {
        FamilyUser familyUser = new FamilyUser();
        familyUser.setPassword("password");

        when(passwordEncoder.encode(eq("password"))).thenReturn("encodedPassword");

        familyUserService.register(familyUser);

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(familyUser);
        assertThat(familyUser.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    void testRegister_WithMatchingPasswords() throws RegistrationFailedException {

        RegistrationCommand command = new RegistrationCommand();
        command.setUsername("user@example.com");
        command.setPassword("password");
        command.setRepeatPassword("password");

        FamilyUser newUser = new FamilyUser("user@example.com", "encodedPassword", "ROLE_ADMIN");
        when(userRepository.findByEmail(eq("user@example.com"))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(eq("password"))).thenReturn("encodedPassword");
        when(userRepository.save(any(FamilyUser.class))).thenReturn(newUser);

        UserDetails result = familyUserService.register(command);

        assertThat(result).isInstanceOf(FamilyUserDetails.class);
        assertThat(((FamilyUserDetails) result).getUsername()).isEqualTo("user@example.com");
    }

    @Test
    void testRegister_WithNonMatchingPasswords() {
        RegistrationCommand command = new RegistrationCommand();
        command.setUsername("user@example.com");
        command.setPassword("password");
        command.setRepeatPassword("differentPassword");

        Throwable thrown = catchThrowable(() -> familyUserService.register(command));

        assertThat(thrown).isInstanceOf(RegistrationFailedException.class)
                .hasMessage("Lozinke se ne podudaraju!");
    }

    @Test
    void testRegister_UserAlreadyExists() {
        RegistrationCommand command = new RegistrationCommand();
        command.setUsername("user@example.com");
        command.setPassword("password");
        command.setRepeatPassword("password");

        when(userRepository.findByEmail(eq("user@example.com"))).thenReturn(Optional.of(new FamilyUser()));

        Throwable thrown = catchThrowable(() -> familyUserService.register(command));

        assertThat(thrown).isInstanceOf(RegistrationFailedException.class)
                .hasMessage("Korisnik već postoji!");
    }
}
