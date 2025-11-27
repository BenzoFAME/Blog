package com.example.simpleblog.Models;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinTable(name = "from_user_id")
    private User fromUser;
    @ManyToOne
    @JoinTable(name = "to_user_id")
    private User toUser;
    private LocalDateTime timestamp = LocalDateTime.now();
}
