package com.example.reddit_clone.likes;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.example.reddit_clone.users.User;
import com.example.reddit_clone.posts.Post;

@Entity
@Table(name = "post_likes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt;

    public PostLike() {
        this.createdAt = LocalDateTime.now();
    }
}
