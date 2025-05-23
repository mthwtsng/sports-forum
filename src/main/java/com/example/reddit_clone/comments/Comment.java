package com.example.reddit_clone.comments;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.example.reddit_clone.users.User;
import com.example.reddit_clone.posts.Post;

@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;


    private LocalDateTime createdAt;

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setPost(Post post){
        this.post = post;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public String getContent() {
        return content;
    }


}