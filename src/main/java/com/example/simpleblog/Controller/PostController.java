package com.example.simpleblog.Controller;

import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Service.CommentService;
import com.example.simpleblog.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }
    @PostMapping("/add")
    public String addPost(@ModelAttribute Post post , @RequestParam("file1")MultipartFile file1, @RequestParam("file2")MultipartFile file2, @RequestParam("file3")MultipartFile file3 , Principal principal) throws IOException {
        postService.addPost(post , file1,file2,file3 , principal.getName());
        return "redirect:/";
    }
    @GetMapping("/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "add";
    }
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id ,  Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "edit";
    }
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
        postService.updatePost(id, post);
        return "redirect:/posts/view/" + id;
    }
    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable Long id , Model model) {
        Post post = postService.findById(id);
        List<Comment> comments = commentService.getCommentsByPostId(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "view";
    }
    @PostMapping("/like/{id}")
    @ResponseBody
    public String addLike(@PathVariable Long id){
        postService.addLike(id);
        return String.valueOf(postService.findById(id).getLikes());
    }
}

