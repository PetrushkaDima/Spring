package com.MySpringApplication.controller;

import com.MySpringApplication.model.Role;
import com.MySpringApplication.model.User;
import com.MySpringApplication.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public String allUsers(Model model) {
        Iterable<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String getUserEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("editUser")
    public String editUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);
        user.setEmail(email);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userService.addUser(user);
        return "redirect:/user/all";
    }

    @GetMapping("profile")
    public String profile(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("profile")
    public String editProfile(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam String email,
            @RequestParam String password
    ) {
        userService.updateProfile(user, email, password);
        model.addAttribute("message", "Данные изменены");
        return "redirect:/user/profile";
    }
}
