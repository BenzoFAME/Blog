package com.example.simpleblog.Controller;

import com.example.simpleblog.Models.Message;
import com.example.simpleblog.Models.User;
import com.example.simpleblog.Service.MessageService;
import com.example.simpleblog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;

    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }
    @GetMapping
    public String inbox(Model model , Principal principal) {
        List<User> chats = messageService.getChatPartners(principal.getName());
        model.addAttribute("chats", chats);
        return "messages";
    }
    @GetMapping("/{userId}")
    public String openChat(@PathVariable Long userId , Principal principal , Model model) {
        List<Message> chat = messageService.getChat(principal.getName(),  userId);
        User partner = userService.findById(userId);
        model.addAttribute("chat", chat);
        model.addAttribute("partner", partner);
        return "chat";
    }
    @PostMapping("/send")
    public String sendMessage(@RequestParam Long userId , @RequestParam String text , Principal principal) {
        messageService.sendMessage(principal.getName(),  userId, text);
        return "redirect:/messages/" + userId;
    }
}
