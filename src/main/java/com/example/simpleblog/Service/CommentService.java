package com.example.simpleblog.Service;

import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Repository.CommentRepository;
import com.example.simpleblog.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("not found"));
        return commentRepository.findByPost(post);
    }
    public Comment addComment(Long postId, String text) {
        Post post = postRepository
                .findById(postId).orElseThrow(() -> new RuntimeException("not found"));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setText(text);
        return commentRepository.save(comment);
    }
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    public Comment updateComment(Long commentId, String text) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setText(text);
        return commentRepository.save(comment);
    }
    public List<Comment> getRecentCommentsByPostId(Long postId , int limit) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("not found"));
        return commentRepository.findTop3ByPostOrderByIdDesc(post);
    }
}
