package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Notification {
    @Id
    private String id;
    private String type;
    private String text;
    private boolean readed;
    private String creationDate;
}
