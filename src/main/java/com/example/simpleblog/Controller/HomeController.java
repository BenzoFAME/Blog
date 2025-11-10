package com.example.simpleblog.Controller;


import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.Image;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Repository.CommentRepository;
import com.example.simpleblog.Repository.ImageRepository;
import com.example.simpleblog.Service.CommentService;
import com.example.simpleblog.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private final CommentService commentService;
    private final PostService postService;
    private final ImageRepository imageRepository;

    public HomeController(CommentService commentService, PostService postService , ImageRepository imageRepository) {
        this.commentService = commentService;
        this.postService = postService;
        this.imageRepository = imageRepository;
    }
    @GetMapping("/")
    public String home(Model model) {
        List<Post> posts = postService.findAll();

        Map<Long, List<Comment>> postComments = new HashMap<>();
        for (Post post : posts) {
            List<Comment> comments = commentService.getRecentCommentsByPostId(post.getId(), 3);
            postComments.put(post.getId(), comments);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("postComments", postComments);
        return "index";
    }
}
