package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.Comment;
import com.grupo02.toctoc.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Obtener todos los comentarios
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Obtener un comentario por su ID
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    // Crear un nuevo comentario
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Actualizar un comentario existente
    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
        
        comment.setContent(commentDetails.getContent());
        comment.setCreationDate(commentDetails.getCreationDate());
        comment.setAuthor(commentDetails.getAuthor());
        comment.setPost(commentDetails.getPost());
        return commentRepository.save(comment);
    }