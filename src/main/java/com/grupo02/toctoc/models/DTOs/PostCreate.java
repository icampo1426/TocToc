package com.grupo02.toctoc.models.DTOs;

import lombok.Data;

@Data
public class PostCreate {
    private String title;
    private String content;
    private String location;

}