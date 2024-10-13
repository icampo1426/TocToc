package com.grupo02.toctoc.repository;

import com.grupo02.toctoc.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Métodos adicionales de búsqueda
}
