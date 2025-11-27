package com.example.simpleblog.Repository;

import com.example.simpleblog.Models.Comment;
import com.example.simpleblog.Models.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findByCommentId(Long commentId);
}
