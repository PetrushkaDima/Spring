package com.MySpringApplication.controller;

import com.MySpringApplication.model.Role;
import com.MySpringApplication.model.User;
import com.MySpringApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("registration")
    public String addUser(User user, Model model) {
        User userFromDB = userService.getUserByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("message", "Такой юзер есть уже");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(user);
        return "redirect:/login";
    }
}
