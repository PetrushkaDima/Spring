package com.MySpringApplication.controller;

import com.MySpringApplication.model.Message;
import com.MySpringApplication.model.User;
import com.MySpringApplication.service.MessageService;
import com.MySpringApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String startPage() {
        return "redirect:/main";
    }

    @GetMapping("main")
    public String mainPage(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageService.getMessageByTag(filter);
        } else {
            messages = messageService.getAllMessage();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("addMessage")
    public String addMessage(@AuthenticationPrincipal User user,
                             @RequestParam String tittle, @RequestParam String text,
                             @RequestParam String tag,
                             @RequestParam("file") MultipartFile file) throws IOException {
        Message message = new Message(tittle, text, tag, user);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String resultFile = uuid + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFile));
            message.setImage(resultFile);
        }
        messageService.saveMessage(message);
        return "redirect:/main";
    }
}
