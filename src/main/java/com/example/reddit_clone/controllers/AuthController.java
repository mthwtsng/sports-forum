package com.example.reddit_clone.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.reddit_clone.model.User;
import com.example.reddit_clone.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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


    /**
     * Login for user by authenticating user info 
     * @param loginData contains the username and password.
     * @param request the HTTP request object to manage the session.
     * @return 200 if OK, error otherwise
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // Authenticates user and set user in security context
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Store security context in created session
        HttpSession session = request.getSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return ResponseEntity.ok().build();
    }
    


    /**
     * Registers user into user repository
     * @param user user details containing username, email, password
     * @return 200 OK if signup is successful, or 400 BAD REQUEST if user/email taken
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        // Check if username or email is already in use
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap("error", "Username or email already taken."));
        }
        // Encodes password, then saves info to user repo
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        userRepository.save(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User created successfully"));
    }

    /**
     * Retrieves the currently user in the session
     * @param authentication the authentication object containing user details
     * @return the authenticated user's details, or 401 UNAUTHORIZED if not authenticated
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        return ResponseEntity.ok(authentication.getPrincipal());
    }



    /**
     * Logs out the user by invalidating the session and clearing the security context
     * @param request the HTTP request object to access the session.
     * @return 200 OK 
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful"));
    }

}
