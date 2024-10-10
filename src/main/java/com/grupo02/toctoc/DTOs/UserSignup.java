package com.grupo02.toctoc.DTOs;

import lombok.Data;

@Data
public class UserSignup {
    private String name;
    private String lastname;
    private String email;
    private String password; // Probablemente quieras cifrar esto


}
