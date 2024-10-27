package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "advertising")
public class Advertising {

    @Id
    @GeneratedValue()
    private UUID id;
    private String title;
    private String image;
    private String link;
    private String startDate;
    private String endDate;

}
