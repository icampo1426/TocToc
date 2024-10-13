package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.Notification;
import com.grupo02.toctoc.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Obtener todas las notificaciones
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Obtener una notificación por su ID
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    // Crear una nueva notificación
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Actualizar una notificación
    public Notification updateNotification(Long id, Notification notificationDetails) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
        
        notification.setType(notificationDetails.getType());
        notification.setText(notificationDetails.getText());
        notification.setReaded(notificationDetails.isReaded());
        notification.setCreationDate(notificationDetails.getCreationDate());

        return notificationRepository.save(notification);
    }

    // Eliminar una notificación por su ID
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    // Métodos adicionales de lógica empresarial (por ejemplo, buscar por usuario)
    public List<Notification> getNotificationsByUserId(Long userId) {
        // Implementar lógica para buscar notificaciones por ID de usuario
        return notificationRepository.findByUserId(userId); // Asegúrate de tener este método en el repositorio
    }
}
