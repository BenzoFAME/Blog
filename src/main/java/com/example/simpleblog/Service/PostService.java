package com.example.simpleblog.Service;

import com.example.simpleblog.Models.Image;
import com.example.simpleblog.Models.Post;
import com.example.simpleblog.Models.User;
import com.example.simpleblog.Repository.ImageRepository;
import com.example.simpleblog.Repository.PostRepository;
import com.example.simpleblog.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository , ImageRepository imageRepository , UserRepository userRepository) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
    public void addLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }
    public void addPost(Post post , MultipartFile file1 , MultipartFile file2 , MultipartFile file3 ,String username) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("not found"));
        List<MultipartFile> files = List.of(file1, file2, file3);
        boolean previewSet = false;
        for (MultipartFile file : files) {
            if (file != null && file.getSize() != 0){
                Image image = toImageEntity(file);
                if (!previewSet){
                    image.setPreviewImages(true);
                    previewSet = true;
                }
                post.addImageToPost(image);
            }
        }
        post.setAuthor(user);
        Post savedPost = postRepository.save(post);
        if (!savedPost.getImages().isEmpty()){
            savedPost.setPreviewImageId(savedPost.getImages().get(0).getId());
            postRepository.save(savedPost);
        }
    }


    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }


    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    public Post updatePost(Long id, Post updated) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        post.setTitle(updated.getTitle());
        post.setContent(updated.getContent());
        return postRepository.save(post);
    }
}
