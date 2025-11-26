package com.example.simpleblog.Service;

import com.example.simpleblog.Models.User;
import com.example.simpleblog.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }
}
