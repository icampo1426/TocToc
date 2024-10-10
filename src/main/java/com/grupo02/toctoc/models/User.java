package com.grupo02.toctoc.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private String id;
    private String name;
    private String lastname;
    private String email;
    private String profileImage;
    private String bio;
}
