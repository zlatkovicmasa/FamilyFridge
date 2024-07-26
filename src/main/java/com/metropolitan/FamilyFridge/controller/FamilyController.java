package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.service.FamilyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    FamilyUserService familyUserService;

    @GetMapping
    public String viewFamily(Model model) {
        List<FamilyUser> familyUsers = familyUserService.findAll();
        model.addAttribute("users", familyUsers);
        return "family";
    }

    @GetMapping("/add")
    public String addFamilyMemberForm(Model model) {
        model.addAttribute("familyMember", new FamilyUser());
        return "add_user";
    }

    @PostMapping("/add")
    public String addFamilyMember(@ModelAttribute FamilyUser familyUser) {
        familyUser.setRole("ROLE_USER");
        familyUserService.register(familyUser);
        return "redirect:/family";
    }
}
