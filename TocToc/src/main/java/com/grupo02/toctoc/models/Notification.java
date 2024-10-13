package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications") // Puedes especificar el nombre de la tabla si es diferente
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cambié a Long para usar generación automática
    private Long id;

    private String type;
    private String text;

    @Column(name = "is_read") // Para seguir las convenciones de nombres
    private boolean readed; // Considera cambiar el nombre a 'isRead' para mayor claridad

    private LocalDateTime creationDate; // Usar LocalDateTime para manejar fechas y horas de manera más efectiva

    // Agrega un campo para relacionar la notificación con el usuario (si corresponde)
    @ManyToOne
    @JoinColumn(name = "user_id") // Cambia "user_id" según el nombre de la columna en la base de datos
    private User user; // Suponiendo que tienes una entidad User
}
