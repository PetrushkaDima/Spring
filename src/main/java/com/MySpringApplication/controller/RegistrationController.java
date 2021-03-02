package com.MySpringApplication.controller;

import com.MySpringApplication.model.User;
import com.MySpringApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("registration")
    public String addUser(
            @RequestParam("password2") String password2,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean password2Empty = StringUtils.isEmpty(password2);
        if (password2Empty) {
            model.addAttribute("password2Error", "Пароль не должен быть пустым");
        }
        if (user.getPassword() != null && !user.getPassword().equals(password2)) {
            model.addAttribute("passwordError", "Пароли различаются");
        }
        if (bindingResult.hasErrors() || password2Empty) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameCloneError", "Пользователь с таким именем уже существует.");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message","Пользователь активирован");
        } else {
            model.addAttribute("message","Код активации не найден");
        }
        return "login";
    }
}
