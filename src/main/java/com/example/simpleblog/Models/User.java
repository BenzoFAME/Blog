package com.example.simpleblog.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
