package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    private String id;
    private String content;
    @ManyToOne
    private User author;
    private String creationDate;
}
