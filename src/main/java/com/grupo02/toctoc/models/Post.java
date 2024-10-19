package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String videoUrl;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    private String creationDate;
    private String location;

    public String getAuthorName() {
        return author != null ? author.getName() : null;
    }
}

