package com.grupo02.toctoc.models;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String identityId;
    private String name;
    private String lastname;
    private String email;

    @ManyToOne
    private FileEntity profileImage;
    @ManyToOne
    private FileEntity bannerImage;

    private String bio;

    private String gender;

    private int level = 1;
}
