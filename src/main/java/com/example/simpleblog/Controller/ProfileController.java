package com.example.simpleblog.Controller;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Models.User;
import com.example.simpleblog.Service.CommentService;
import com.example.simpleblog.Service.PostService;
import com.example.simpleblog.Service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    public ProfileController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }
    @GetMapping
    public String myProfile(Principal principal , Model model ) {
        User user = userService.findByUsername(principal.getName());
        List<Post> posts = postService.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "profile";
    }
    @GetMapping("/{userId}")
    public String viewProfile(Model model, @PathVariable Long userId) {
        User profileUser = userService.findById(userId);
        List<Post> posts= postService.findByUser(profileUser);
        model.addAttribute("user", profileUser);
        model.addAttribute("posts", posts);
        return "profile";
    }
    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "editProfile";
    }
    @PostMapping("/edit")
    public String updateProfile(Principal principal , Model model , @RequestParam String username , @RequestParam("avatar") MultipartFile avatar) throws IOException {
        User user = userService.findByUsername(principal.getName());
        if (user != null && username.isBlank()) {
            user.setUsername(username);
        }
        if (avatar != null && !avatar.isEmpty()) {
            user.setAvatar(avatar.getBytes());
        }
        userService.save(user);
        return "redirect:/profile";
    }
    @GetMapping("/avatar/{userId}")
    public ResponseEntity<ByteArrayResource> avatar(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if(user == null || user.getAvatar() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new ByteArrayResource(user.getAvatar()));
    }
}
