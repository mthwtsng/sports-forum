package com.example.reddit_clone.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reddit_clone.comments.Comment;
import com.example.reddit_clone.comments.CommentRepository;
import com.example.reddit_clone.likes.PostLike;
import com.example.reddit_clone.likes.PostLikeRepository;
import com.example.reddit_clone.users.User;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository  postLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }
    public PostLike addLike(Post post, User user) {
        PostLike like = new PostLike();
        like.setPost(post);
        like.setUser(user);
        return postLikeRepository.save(like);
    }

    public Comment addComment(Post post, User user, Comment parentComment, String content) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setParentComment(parentComment);
        comment.setContent(content);
        return commentRepository.save(comment);
    }
    
}
