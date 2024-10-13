package com.grupo02.toctoc.repository;

import com.grupo02.toctoc.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> { // Cambié a Long
    // Aquí puedes añadir métodos adicionales de búsqueda si los necesitas
    // Por ejemplo:
    // List<Notification> findByUserId(Long userId); // Buscar notificaciones por ID de usuario
    // List<Notification> findByIsRead(boolean isRead); // Buscar notificaciones leídas/no leídas
}

