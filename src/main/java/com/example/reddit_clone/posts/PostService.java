package com.example.reddit_clone.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reddit_clone.comments.Comment;
import com.example.reddit_clone.comments.CommentRepository;
import com.example.reddit_clone.likes.PostLike;
import com.example.reddit_clone.likes.PostLikeRepository;
import com.example.reddit_clone.users.User;
import com.example.reddit_clone.users.UserRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository  postLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }
    public PostLike addLike(Long postId, User user) {
        PostLike like = new PostLike();
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));;
        like.setPost(post);
        like.setUser(user);
        return postLikeRepository.save(like);
    }

    public Comment addComment(Long postId, Long userId, Comment parentComment, String content) {
        Comment comment = new Comment();
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setParentComment(parentComment);
        comment.setContent(content);
        return commentRepository.save(comment);
    }
    
    public Post getLatestPost() {
        return postRepository.findTopByOrderByCreatedDateDesc();
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }
}
