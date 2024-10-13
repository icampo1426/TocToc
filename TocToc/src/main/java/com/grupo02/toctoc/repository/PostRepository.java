package com.grupo02.toctoc.repository;

import com.grupo02.toctoc.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> { // Cambiado a Long
    // Método mejorado para buscar por título, autor y ubicación
    Page<Post> findByTitleContainingAndAuthor_NickNameContainingAndLocationContaining(
            String title, String author, String location, Pageable pageable);
}

