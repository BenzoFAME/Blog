package com.example.simpleblog.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "subcomment")
public class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "author_id" , nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name = "comment_id" , nullable = false)
    private Comment comment;
    private LocalDateTime timestamp =  LocalDateTime.now();
}
