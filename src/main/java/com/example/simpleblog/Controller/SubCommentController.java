package com.example.simpleblog.Controller;

import com.example.simpleblog.Models.SubComment;
import com.example.simpleblog.Repository.SubCommentRepository;
import com.example.simpleblog.Service.SubCommentService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/subcomment")
public class SubCommentController {
    private final SubCommentService subCommentService;
    public SubCommentController(SubCommentService subCommentService) {
        this.subCommentService = subCommentService;
    }
    @PostMapping("/add")
    public String addSubComment(@RequestParam Long commentId,@RequestParam String text ,Principal principal) {
        subCommentService.addSubComment(commentId,principal.getName(),text);
        return "redirect:/posts/view/" + getPostIdByComment(commentId);
    }
    private Long getPostIdByComment(Long commentId){
        return subCommentService.getPostIdByComment(commentId);
    }
}
