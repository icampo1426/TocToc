package com.grupo02.toctoc.services;

import com.grupo02.toctoc.controllers.CommentController;
import com.grupo02.toctoc.models.Comment;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.db.CommentRepository;
import com.grupo02.toctoc.repository.db.PostRepository;
import com.grupo02.toctoc.services.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public void createComment(String comment, UUID postId, User user) throws NotFoundException {
        Post  post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        Comment newComment = new Comment();
        newComment.setContent(comment);
        newComment.setAuthor(user);
        newComment.setPost(post);
        commentRepository.save(newComment);
    }

    // Métodos de lógica empresarial
}
