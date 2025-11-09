package com.example.simpleblog.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    @OneToMany(mappedBy = "post" ,  cascade = CascadeType.ALL , orphanRemoval = true)
    List<Image> images = new ArrayList<>();
    private Long previewImageId;

    public void addImageToPost(Image image){
        image.setPost(this);
        images.add(image);
    }
}
