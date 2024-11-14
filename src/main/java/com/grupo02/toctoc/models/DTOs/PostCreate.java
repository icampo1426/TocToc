package com.grupo02.toctoc.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class PostCreate {
    private String title;
    private String content;
    private String location;

}