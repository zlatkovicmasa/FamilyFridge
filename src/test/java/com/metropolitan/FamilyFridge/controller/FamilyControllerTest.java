package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.service.FamilyUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FamilyControllerTest {

    private FamilyController familyController;
    private FamilyUserService mockFamilyUserService;
    private Model model;

    @BeforeEach
    public void setUp() {
        mockFamilyUserService = mock(FamilyUserService.class);
        familyController = new FamilyController();
        familyController.familyUserService = mockFamilyUserService;
        model = mock(Model.class);
    }

    @Test
    public void testViewFamily() throws Exception {
        List<FamilyUser> familyUsers = List.of(new FamilyUser(), new FamilyUser());
        when(mockFamilyUserService.findAll()).thenReturn(familyUsers);

        String viewName = familyController.viewFamily(model);

        assertThat(viewName).isEqualTo("family");
        verify(model).addAttribute("users", familyUsers);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddFamilyMemberForm() {
        String viewName = familyController.addFamilyMemberForm(model);

        assertThat(viewName).isEqualTo("add_user");
        verify(model).addAttribute(eq("familyMember"), any(FamilyUser.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddFamilyMember() {
        FamilyUser familyUser = new FamilyUser();
        familyUser.setEmail("test@example.com");
        familyUser.setPassword("password");

        String viewName = familyController.addFamilyMember(familyUser);

        assertThat(viewName).isEqualTo("redirect:/family");
        assertThat(familyUser.getRole()).isEqualTo("ROLE_USER");
        verify(mockFamilyUserService).register(familyUser);
    }
}
