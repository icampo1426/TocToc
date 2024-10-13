package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500) // Asegura que el contenido no sea nulo y limita la longitud
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Relación con el autor del comentario
    private User author;

    @Column(nullable = false) // Asegura que la fecha de creación no sea nula
    private String creationDate;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Relación con el post al que pertenece el comentario
    private Post post; // Asegúrate de tener un modelo de Post
}
