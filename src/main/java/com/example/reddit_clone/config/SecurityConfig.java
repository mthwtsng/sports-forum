package com.example.reddit_clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/hello").permitAll()  // Allow unauthenticated access to /api/hello
                .anyRequest().authenticated()  // Require authentication for all other requests
            )
            .csrf(csrf -> csrf.disable());  // Disable CSRF for development purposes

        return http.build();
    }
}
