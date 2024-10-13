package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.DTOs.PostCreate;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Obtener publicaciones con filtros y paginación
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset) {

        Map<String, Object> response = postService.getPosts(title, author, location, limit, offset);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Crear una nueva publicación
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostCreate postCreate) {
        Post newPost = postService.createPost(postCreate);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    // Obtener una publicación por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return post != null ? new ResponseEntity<>(post, HttpStatus.OK) 
                            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Actualizar una publicación
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostCreate postCreate) {
        Post updatedPost = postService.updatePost(id, postCreate);
        return updatedPost != null ? new ResponseEntity<>(updatedPost, HttpStatus.OK) 
                                   : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar una publicación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean isDeleted = postService.deletePost(id);
        return isDeleted ? ResponseEntity.ok().build() 
                         : ResponseEntity.notFound().build();
    }
}

