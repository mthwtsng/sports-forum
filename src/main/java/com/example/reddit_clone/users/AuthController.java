package com.example.reddit_clone.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.reddit_clone.users.User;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login for user by authenticating user info 
     * @param loginData contains the username and password.
     * @param request the HTTP request object to manage the session.
     * @return 200 if OK, error otherwise
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        return authService.login(loginData, request);
    }

    /**
     * Registers user into user repository
     * @param user user details containing username, email, password
     * @return 200 OK if signup is successful, or 400 BAD REQUEST if user/email taken
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        return authService.signup(user);
    }

    /**
     * Retrieves the currently user in the session
     * @param authentication the authentication object containing user details
     * @return the authenticated user's details, or 401 UNAUTHORIZED if not authenticated
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        return authService.getCurrentUser(authentication);
    }

    /**
     * Logs out the user by invalidating the session and clearing the security context
     * @param request the HTTP request object to access the session.
     * @return 200 OK 
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authService.logout(request);
    }
}