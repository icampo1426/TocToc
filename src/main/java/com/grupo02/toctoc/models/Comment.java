package com.grupo02.toctoc.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private UUID id;
    private String content;
    @ManyToOne
    private User author;
    private String creationDate;

    @ManyToOne
    @Nullable
    private Comment parent;
}
