package com.example.reddit_clone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.reddit_clone.model.User;
import com.example.reddit_clone.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        ArrayList<User> userlist = userRepository.findByUsernameAndPassword(username, password);
        if (userlist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Username/Password");
        } else {
            return ResponseEntity.ok("Successfully Logged in");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        // Check if the user already exists, etc.
        // Assuming userService creates a new user
        try {
            userRepository.save(user);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "User created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap("message", "User creation failed: " + e.getMessage()));
        }
    }

}

