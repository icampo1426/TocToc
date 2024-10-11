package com.grupo02.toctoc.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Add {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private String link;
    private String startDate;
    private String endDate;


}
