package com.example.reddit_clone.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Table;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    
}
