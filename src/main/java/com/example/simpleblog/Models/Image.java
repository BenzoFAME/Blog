package com.example.simpleblog.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "originalFileName")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "contentType")
    private String contentType;
    @Column(name = "isPreviewImages")
    private boolean isPreviewImages;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] bytes;
    @ManyToOne(cascade = CascadeType.REFRESH , fetch = FetchType.EAGER)
    private Post post;
}
