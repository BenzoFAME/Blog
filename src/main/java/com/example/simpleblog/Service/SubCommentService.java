package com.example.simpleblog.Service;

import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.SubComment;
import com.example.simpleblog.Models.User;
import com.example.simpleblog.Repository.CommentRepository;
import com.example.simpleblog.Repository.PostRepository;
import com.example.simpleblog.Repository.SubCommentRepository;
import com.example.simpleblog.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCommentService {
    private final SubCommentRepository subCommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    public SubCommentService(SubCommentRepository subCommentRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.subCommentRepository = subCommentRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }
    public void addSubComment(Long commentId, String username , String text) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("comment not found"));
        User author = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("author not found"));
        SubComment subComment = new SubComment();
        subComment.setText(text);
        subComment.setComment(comment);
        subComment.setAuthor(author);
        subCommentRepository.save(subComment);
    }

    public Long getPostIdByComment(Long id) {
        return commentRepository.findById(id).map(c -> c.getPost().getId()).orElseThrow(()-> new RuntimeException("post not found"));
    }
    public List<SubComment> getSubCommentsByCommentId(Long commentId) {
        return subCommentRepository.findByCommentId(commentId);
    }
}
