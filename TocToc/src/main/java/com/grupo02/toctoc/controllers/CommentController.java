package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Métodos RESTful, análogos a las especificaciones en OpenAPI
}
