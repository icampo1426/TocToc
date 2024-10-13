package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID
    private Long id; // Cambiado a Long
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading para evitar cargas innecesarias
    @JoinColumn(name = "author_id") // Especificar la columna de unión
    private User author;

    private LocalDateTime creationDate; // Cambiado a LocalDateTime para manejar fechas
    private String location;
}
