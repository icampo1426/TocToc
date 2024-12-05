package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Favorito {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private User user;
    @OneToMany
    private List<Post> favoritos = new ArrayList<>();
}
