package com.example.reddit_clone.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Table;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
}