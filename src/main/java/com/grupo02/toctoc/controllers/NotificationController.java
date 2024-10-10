package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Métodos RESTful, análogos a las especificaciones en OpenAPI
}
