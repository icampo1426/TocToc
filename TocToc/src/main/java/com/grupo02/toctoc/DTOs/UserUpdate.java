package com.grupo02.toctoc.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdate {

    private String name;

    private String lastname;

    @Pattern(regexp = "(https?:\\/\\/.*\\.(?:png|jpg|jpeg))?", message = "La URL de la imagen de perfil no es válida")
    private String profileImage;

    @Pattern(regexp = "(https?:\\/\\/.*\\.(?:png|jpg|jpeg))?", message = "La URL de la imagen de banner no es válida")
    private String bannerImage;

    private String bio;

    private int gender; // Puedes agregar un validador específico para el rango de valores si es necesario
}
