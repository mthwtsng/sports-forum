package com.example.reddit_clone.posts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reddit_clone.comments.Comment;
import com.example.reddit_clone.likes.PostLike;
import com.example.reddit_clone.users.User;
import com.example.reddit_clone.users.UserRepository;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    private UserRepository userRepository;
    
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(postService.createPost(post, user));
    }
    
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    
    @PostMapping("/{postId}/like")
    public ResponseEntity<PostLike> addLike(@PathVariable Long postId, @RequestBody User user) {
        return ResponseEntity.ok(postService.addLike(postId, user));
    }
    
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment, @RequestParam Long userId) {
        return ResponseEntity.ok(postService.addComment(postId, userId, comment.getParentComment(), comment.getContent()));
    }

    @GetMapping("/latest")
    public ResponseEntity<Post> getLatestPost() {
        Post latestPost = postService.getLatestPost();
        return latestPost != null ? ResponseEntity.ok(latestPost) : ResponseEntity.noContent().build();
    }

}
