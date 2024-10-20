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

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createPost(@ModelAttribute PostCreate postCreate) {
        try {
            Post post = postService.createPost(postCreate);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Solicitud inválida. Revisa los datos proporcionados.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>("Post eliminado con éxito.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el post. Verifica que el ID sea correcto.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @ModelAttribute PostCreate postCreate) {
        try {
            Post updatedPost = postService.updatePost(id, postCreate);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el post. Revisa los datos proporcionados.", HttpStatus.BAD_REQUEST);
        }
    }
}
