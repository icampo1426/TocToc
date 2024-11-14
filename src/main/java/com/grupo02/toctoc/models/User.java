package com.grupo02.toctoc.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;
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

    @JsonIgnore
    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRelationship> sentRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRelationship> receivedRequests;


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", sentRequests=" + (sentRequests != null ? sentRequests.size() : "null") +
                ", receivedRequests=" + (receivedRequests != null ? receivedRequests.size() : "null") +
                '}';
    }}
