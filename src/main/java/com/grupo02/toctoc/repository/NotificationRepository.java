package com.grupo02.toctoc.repository;

import com.grupo02.toctoc.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    // Métodos adicionales de búsqueda
}
