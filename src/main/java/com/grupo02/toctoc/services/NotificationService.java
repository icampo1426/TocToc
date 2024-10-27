package com.grupo02.toctoc.services;

import com.grupo02.toctoc.repository.db.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Métodos de lógica empresarial
}