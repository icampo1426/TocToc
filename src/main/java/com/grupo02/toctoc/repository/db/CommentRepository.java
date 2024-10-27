package com.grupo02.toctoc.repository.db;

import com.grupo02.toctoc.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    // Métodos adicionales de búsqueda
}
