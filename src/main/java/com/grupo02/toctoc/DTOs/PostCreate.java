package com.grupo02.toctoc.DTOs;

import lombok.Data;

@Data
public class PostCreate {
    private String title;
    private String content;
    private String location;
    private Long authorId;


}