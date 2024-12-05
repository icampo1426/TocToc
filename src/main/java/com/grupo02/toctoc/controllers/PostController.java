package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.DTOs.PostCreate;
import com.grupo02.toctoc.models.Post;
import com.grupo02.toctoc.models.User;
import com.grupo02.toctoc.services.PostService;
import com.grupo02.toctoc.utils.AuthUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public ResponseEntity<Page<Post>> getPosts(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> response = postService.getPosts(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable UUID userId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> posts = postService.getPostsByUserId(userId, pageable);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/my-friends")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<Post>> getPostsByMyFriends(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);

        if (userAuth.isPresent()) {
            List<Post> response = postService.getPostsByMyFriends(userAuth.get().getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/my-friends/pages")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<Page<Post>> getPostsByMyFriendspages(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);

        if (userAuth.isPresent()) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Post> response = postService.getPostsByMyFriends(userAuth.get().getId(), pageable);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

    @PostMapping(value = "/withFiles",consumes = {"multipart/form-data"})
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<Post> createPost(@RequestPart("file") List<MultipartFile> file,
                                           @RequestPart String  title,
                                           @RequestPart String  content,
                                           @RequestPart String  location) {
        Optional<User> userAuth = AuthUtils.getCurrentAuthUser(User.class);


        if (userAuth.isPresent()) {
            Post newPost = postService.createPost(userAuth.get(), new PostCreate(title,content,location), file);
            return new ResponseEntity<>(newPost, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
