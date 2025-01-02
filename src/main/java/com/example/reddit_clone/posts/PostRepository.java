package com.example.reddit_clone.posts;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.users.User;



@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthor(User author);
    Optional<Post> findById(Long PostId);
}
