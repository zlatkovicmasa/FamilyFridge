package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.entity.RegistrationCommand;
import com.metropolitan.FamilyFridge.exception.RegistrationFailedException;
import com.metropolitan.FamilyFridge.security.FamilyUserDetails;
import com.metropolitan.FamilyFridge.service.FamilyUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.swing.text.View;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {

    private UserController userController;
    private FamilyUserService mockFamilyUserService;
    private Model model;
    private HttpServletRequest request;
    private HttpSession session;
    private BindingResult bindingResult;
    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        mockFamilyUserService = mock(FamilyUserService.class);
        userController = new UserController();
        userController.familyUserService = mockFamilyUserService;
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        bindingResult = mock(BindingResult.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);

        SecurityContextHolder.clearContext();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testShowRegistrationForm_WhenAuthenticated() {


        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(
                new FamilyUser("test@email.com", "password", "ROLE_ADMIN")
        );

        String viewName = userController.showRegistrationForm(model);
        assertThat(viewName).isEqualTo("redirect:/home");
    }

    @Test
    void testShowRegistrationForm_WhenNotAuthenticated() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        String viewName = userController.showRegistrationForm(model);

        assertThat(viewName).isEqualTo("register");
        verify(model).addAttribute("registrationCommand", new RegistrationCommand());
    }

    @Test
    void testRegister_Success() throws RegistrationFailedException {

        RegistrationCommand registrationCommand = new RegistrationCommand("test@email.com", "password123", "password123");
        FamilyUserDetails expectedUserDetails = new FamilyUserDetails(new FamilyUser("test@email.com", "password123", "ROLE_ADMIN"));
        when(mockFamilyUserService.register(registrationCommand)).thenReturn(expectedUserDetails);
        when(request.getSession(true)).thenReturn(session);

        assertThat(userController.register(model, registrationCommand, bindingResult, request)).isEqualTo("redirect:/home");
        verify(session).setAttribute(eq("SPRING_SECURITY_CONTEXT"), any());
    }

    @Test
    void testRegister_Failure() throws RegistrationFailedException {

        RegistrationCommand registrationCommand = new RegistrationCommand();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("test", "Error message")));

        String viewName = userController.register(model, registrationCommand, bindingResult, request);

        assertThat(viewName).isEqualTo("register");
        verify(model).addAttribute("error", "Error message");
    }

    @Test
    void testRegister_Exception() throws RegistrationFailedException {

        RegistrationCommand registrationCommand = new RegistrationCommand();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(mockFamilyUserService.register(any(RegistrationCommand.class)))
                .thenThrow(new RegistrationFailedException("Registration failed"));

        String viewName = userController.register(model, registrationCommand, bindingResult, request);

        assertThat(viewName).isEqualTo("register");
        verify(model).addAttribute("error", "Registration failed");
    }

    @Test
    void testShowLoginForm() {

        String viewName = userController.showLoginForm();
        assertThat(viewName).isEqualTo("login");
    }
}
