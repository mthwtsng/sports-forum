package com.example.reddit_clone.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/home")
    public String login() {
        return "index";  // This returns the login.html Thymeleaf template
    }
}
