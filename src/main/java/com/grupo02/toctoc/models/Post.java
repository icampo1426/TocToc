package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String content;
    @ManyToOne
    private User author;
    private String creationDate;
    private String location;
}
