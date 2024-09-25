package com.example.reddit_clone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit_clone.model.User;
import com.example.reddit_clone.repository.UserRepository;

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
    private UserRepository userRepo;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userRepo.findByUsername(loginUser.getUsername());
        
        if (user != null && user.getPassword().equals(loginUser.getPassword())) { // Remember to hash passwords in production
            return ResponseEntity.ok("User Created");
        } else {
            return ResponseEntity.badRequest().body("User Not Found");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> Signup(@RequestBody User signupUser) {
        if(userRepo.findByUsername(signupUser.getUsername()) != null){
            return ResponseEntity.badRequest().body("Username already in use.");
        }
        userRepo.save(signupUser);
        return ResponseEntity.ok("User Successfully Signed up");
    }
    
    
}
