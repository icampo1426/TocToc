package com.grupo02.toctoc.repository;

import com.grupo02.toctoc.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {
    // Métodos adicionales de búsqueda
}

