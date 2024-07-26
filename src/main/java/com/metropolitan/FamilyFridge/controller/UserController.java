package com.metropolitan.FamilyFridge.controller;

import com.metropolitan.FamilyFridge.entity.FamilyUser;
import com.metropolitan.FamilyFridge.entity.RegistrationCommand;
import com.metropolitan.FamilyFridge.exception.RegistrationFailedException;
import com.metropolitan.FamilyFridge.repository.UserRepository;
import com.metropolitan.FamilyFridge.service.FamilyUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private FamilyUserService familyUserService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/home";
        }
        model.addAttribute("registrationCommand", new RegistrationCommand());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute RegistrationCommand registrationCommand,
                           BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            UserDetails registeredUserDetails = familyUserService.register(registrationCommand);
            Authentication auth = new UsernamePasswordAuthenticationToken(registeredUserDetails,
                    null, registeredUserDetails.getAuthorities());

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            return "redirect:/home";
        } catch (RegistrationFailedException e) {
            model.addAttribute("error", e.getMessage());
            return "register";

        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
