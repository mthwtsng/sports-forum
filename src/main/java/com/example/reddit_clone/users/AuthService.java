package com.example.reddit_clone.users;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.example.reddit_clone.users.User;
import com.example.reddit_clone.users.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticates user and stores security context in session
     * @param loginData the login data containing username and password
     * @param request the HTTP request to manage session
     * @return ResponseEntity with status of login attempt
     */
    public ResponseEntity<?> login(Map<String, String> loginData, HttpServletRequest request) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return ResponseEntity.ok().build();
    }

    /**
     * Registers a new user if username/email is available
     * @param user the user details to be saved
     * @return ResponseEntity with the result of the signup attempt
     */
    public ResponseEntity<?> signup(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap("error", "Username or email already taken."));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User created successfully"));
    }

    /**
     * Retrieves the currently authenticated user
     * @param authentication the authentication object
     * @return ResponseEntity with user details or UNAUTHORIZED if not authenticated
     */
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
    
        String username = authentication.getName();
    
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    
        // Return the full User entity
        // Return a DTO instead of the full User entity
        Map<String, Object> userDto = Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "email", user.getEmail()
        );
        return ResponseEntity.ok(userDto);
    }

    /**
     * Logs out the user by invalidating the session and clearing the security context
     * @param request the HTTP request object
     * @return ResponseEntity with logout success message
     */
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful"));
    }
}