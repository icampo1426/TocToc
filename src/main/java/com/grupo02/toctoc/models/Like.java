package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "likes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post postId;
}
