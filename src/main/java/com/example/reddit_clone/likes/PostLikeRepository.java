package com.example.reddit_clone.likes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit_clone.posts.Post;
import com.example.reddit_clone.users.User;



@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    List<PostLike> findByPost(Post post);
    List<PostLike> findByUser(User user);
}