package com.grupo02.toctoc.models.DTOs;
import lombok.Data;

@Data
public class PostUpdate {
    private String title;
    private String content;
    private String location;

}