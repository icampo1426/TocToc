package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.DTOs.PostCreate;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.services.PostService;
import com.grupo02.toctoc.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

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

    @PostMapping
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<Post> createPost(@RequestBody PostCreate postCreate) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);

        if(userAuth.isPresent()){

            Post newPost = postService.createPost(userAuth.get(),postCreate);

            return new ResponseEntity<>(newPost, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
