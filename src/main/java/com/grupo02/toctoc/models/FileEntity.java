package com.grupo02.toctoc.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class FileEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String uri;
    private FileType type;
}
