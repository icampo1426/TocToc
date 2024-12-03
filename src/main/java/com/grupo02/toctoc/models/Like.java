package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    private String id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post postId;
}
