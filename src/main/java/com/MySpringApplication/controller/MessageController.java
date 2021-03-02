package com.MySpringApplication.controller;

import com.MySpringApplication.model.Message;
import com.MySpringApplication.model.User;
import com.MySpringApplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {
        Set<Message> messages = user.getMessages();
        model.addAttribute("channelUser", user);
        model.addAttribute("subscriberCount", user.getSubscribers().size());
        model.addAttribute("subscriptionCount", user.getSubscriptions().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", user.equals(currentUser));
        model.addAttribute("message", message);
        return "userMessages";
    }

    @PostMapping("user-messages/{user}")
    public String editUserMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            Model model,
            @RequestParam("id") Message message,
            @RequestParam("tittle") String tittle,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(tittle)) {
                message.setTittle(tittle);
            }
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            ControllerUtils.saveFile(message, file, uploadPath);
            messageService.saveMessage(message);
        }
        return "redirect:/user-messages/" + user;
    }
}
