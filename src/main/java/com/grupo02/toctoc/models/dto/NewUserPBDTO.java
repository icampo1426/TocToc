package com.grupo02.toctoc.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserPBDTO {

    private String username;
    private String email;
    private boolean emailVisibility;
    private String password;
    private String passwordConfirm;
    private String name;

    public NewUserPBDTO(String name, String lastname, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirm=password;

    }
}
