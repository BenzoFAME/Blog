package com.example.simpleblog.Controller;

import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Service.CommentService;
import com.example.simpleblog.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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
    public String addComment(@PathVariable Long postId,
                             String text,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        commentService.addComment(postId, text, principal.getName());
        return "redirect:/posts/view/" + postId;
    }
    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId , Comment comment, Principal principal) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/view/" + comment.getPost().getId();
    }
    @PostMapping("/update/{commentId}")
    public String updateComment(@PathVariable Long commentId , String text) {
        commentService.updateComment(commentId, text);
        return "redirect:/";
    }
}
