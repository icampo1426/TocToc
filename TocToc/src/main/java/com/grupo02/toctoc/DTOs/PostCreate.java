package com.grupo02.toctoc.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostCreate {
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El contenido es obligatorio")
    private String content;

    private String location;

    @NotNull(message = "El ID del autor es obligatorio")
    private Long authorId;
}
