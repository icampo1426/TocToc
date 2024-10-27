package com.grupo02.toctoc.repository.db;

import com.grupo02.toctoc.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    // Métodos adicionales de búsqueda
}
