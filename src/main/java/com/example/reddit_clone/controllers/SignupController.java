package com.example.reddit_clone.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup"; // Return the signup.html template
    }
}