package com.grupo02.toctoc.DTOs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreate {
    private String title;
    private String content;
    private MultipartFile image;
    private MultipartFile video;
    private Long authorId;
}