package com.example.simpleblog.Repository;

import com.example.simpleblog.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
