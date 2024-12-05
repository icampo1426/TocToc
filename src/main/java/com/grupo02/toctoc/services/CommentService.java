package com.grupo02.toctoc.services;

import com.grupo02.toctoc.controllers.CommentController;
import com.grupo02.toctoc.models.Comment;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.db.CommentRepository;
import com.grupo02.toctoc.repository.db.PostRepository;
import com.grupo02.toctoc.repository.db.UserRepository;
import com.grupo02.toctoc.services.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public void createComment(String comment, UUID postId, User user) throws NotFoundException {
        Post  post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        Comment newComment = new Comment();
        newComment.setContent(comment);
        newComment.setAuthor(user);
        newComment.setPost(post);
        commentRepository.save(newComment);

        int totalPosts = postRepository.countByAuthor(user);
        int totalComments = commentRepository.countByAuthor(user);
        user.recalculateLevel(totalPosts, totalComments);

        userRepository.save(user);
    }

    public List<Comment> getByComments(String postId) throws NotFoundException {
        return commentRepository.findByPost(postRepository.findById(UUID.fromString(postId)).orElseThrow(() -> new NotFoundException("Post not found")));
    }
}
