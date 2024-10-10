package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.services.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adds")
public class AddController {

    @Autowired
    private AddService addService;

    // Métodos RESTful, análogos a las especificaciones en OpenAPI
}
