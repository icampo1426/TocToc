package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    private String lastname;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    private String profileImage;
    private String bannerImage;

    @Size(max = 255)
    private String bio;

    private Gender gender;  // Cambiado a enum
    private int level = 1;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
