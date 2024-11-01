package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class UserRelationship {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User requester;

    @ManyToOne
    private User receiver;

    @Enumerated(EnumType.STRING)
    private RelationshipStatus status;

    public enum RelationshipStatus {
        REQUESTED,
        ACCEPTED,
        REJECTED
    }
}