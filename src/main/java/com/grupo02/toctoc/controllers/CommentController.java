package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.services.CommentService;
import com.grupo02.toctoc.services.exception.NotFoundException;
import com.grupo02.toctoc.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    @SecurityRequirement(name = "bearer")
    public ResponseEntity commentPost(@RequestBody CommentCreate commentCreate) throws NotFoundException {
        User userAuth = AuthUtils.getCurrentAuthUser(User.class).get();
        commentService.createComment(commentCreate.comment, commentCreate.postId, userAuth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity getCommentsByPostId(@RequestParam String postId ) throws NotFoundException {
        User userAuth = AuthUtils.getCurrentAuthUser(User.class).get();
        commentService.getByComments(postId);
        return ResponseEntity.ok().build();
    }

    public record CommentCreate(String comment, UUID postId) {}
}
