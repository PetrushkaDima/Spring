package com.MySpringApplication.controller;

import com.MySpringApplication.model.User;
import com.MySpringApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SubscribeController {
    @Autowired
    UserService userService;

    @GetMapping("/{type}/{user}/list")
    public String userList(
            @PathVariable User user,
            @PathVariable String type,
            Model model
    ) {
        model.addAttribute("channelUser", user);
        model.addAttribute("type", type.equals("subscriptions") ? "Подписки" : "Подписчики");
        model.addAttribute("users", type.equals("subscriptions") ? user.getSubscriptions() : user.getSubscribers());
        return "subscriptions";
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser
    ) {
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @PathVariable User user,
            @AuthenticationPrincipal User currentUser
    ) {
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }
}
