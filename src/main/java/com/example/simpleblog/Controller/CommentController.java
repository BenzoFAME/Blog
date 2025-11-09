package com.example.simpleblog.Controller;

import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Service.CommentService;
import com.example.simpleblog.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;
    private PostService postService;
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }
    @PostMapping("/add/{postId}")
    public String addComment(@PathVariable Long postId , String text) {
        Comment comment = commentService.addComment(postId, text);
        return "redirect:/posts/";
    }
    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/";
    }
    @PostMapping("/update/{commentId}")
    public String updateComment(@PathVariable Long commentId , String text) {
        commentService.updateComment(commentId, text);
        return "redirect:/posts/";
    }
}
