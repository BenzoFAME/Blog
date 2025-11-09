package com.example.simpleblog.Repository;

import com.example.simpleblog.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
