package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Métodos RESTful, análogos a las especificaciones en OpenAPI
}
