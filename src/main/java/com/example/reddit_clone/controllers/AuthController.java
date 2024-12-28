package com.example.reddit_clone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.reddit_clone.model.User;
import com.example.reddit_clone.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;




@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap("error", "Username or email already taken."));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        userRepository.save(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User created successfully"));
    }
}
