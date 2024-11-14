package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
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

    @Override
    public String toString() {
        return "UserRelationship{" +
                "id=" + id +
                ", requester=" + (requester != null ? requester.getEmail() : "null") +
                ", receiver=" + (receiver != null ? receiver.getEmail() : "null") +
                '}';
    }
}