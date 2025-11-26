package com.example.simpleblog.Repository;

import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User user);
}
