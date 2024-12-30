package com.example.reddit_clone.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Table;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}