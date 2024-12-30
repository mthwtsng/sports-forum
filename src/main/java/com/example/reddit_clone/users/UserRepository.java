package com.example.reddit_clone.users;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email); 
    
    Optional<User> findByUsernameAndEmail(String username, String email); 
    
    boolean existsByUsername(String username); 
    
    boolean existsByEmail(String email); 
}
