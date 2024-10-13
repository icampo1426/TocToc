package com.grupo02.toctoc.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUpdate {

    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotBlank(message = "El contenido no puede estar vacío")
    private String content;

    private String location; // Este campo puede ser opcional
}
