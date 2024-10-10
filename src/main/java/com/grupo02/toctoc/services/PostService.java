package com.grupo02.toctoc.services;

import com.grupo02.toctoc.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Métodos de lógica empresarial
}
