package com.grupo02.toctoc.models.DTOs;

import lombok.Data;

@Data
public class UserSignup {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String gender;

}
