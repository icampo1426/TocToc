package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.DTOs.PostCreate;
import com.grupo02.toctoc.models.Like;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.repository.db.LikeRepository;
import com.grupo02.toctoc.repository.db.PostRepository;
import com.grupo02.toctoc.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/like")
public class LikesController {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;


    @PostMapping
    @SecurityRequirement(name = "bearer")
    public ResponseEntity createPost(@RequestBody String postId) {

        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);

        Post post= postRepository.findById(UUID.fromString(postId)).orElseThrow();

        Like newlike= Like.builder().postId(post).user(userAuth.get()).build();

        likeRepository.save(newlike);

        return ResponseEntity.ok().build();
    }
}
