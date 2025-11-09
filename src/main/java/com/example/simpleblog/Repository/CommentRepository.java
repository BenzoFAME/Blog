package com.example.simpleblog.Repository;

import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findTop3ByPostOrderByIdDesc(Post post);
}
