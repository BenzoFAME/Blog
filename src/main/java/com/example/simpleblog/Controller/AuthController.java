package com.example.simpleblog.Controller;

import com.example.simpleblog.Models.User;
import com.example.simpleblog.Repository.UserRepository;
import com.example.simpleblog.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("userForm") User userForm, HttpServletRequest request, Model model){
        if (userRepository.findByEmail(userForm.getEmail()).isPresent()) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "registration";
        }
        if (userRepository.findByUsername(userForm.getUsername()).isPresent()) {
            model.addAttribute("error", "Пользователь с таким username уже существует");
            return "registration";
        }
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        userForm.setRole(Role.ROLE_USER);
        userForm.setEnabled(true);
        userRepository.save(userForm);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userForm.getUsername(),
                null,
                List.of(new SimpleGrantedAuthority(userForm.getRole().name()))
        );
        SecurityContextHolder.getContext().setAuthentication(token);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return "redirect:/";
    }
}

