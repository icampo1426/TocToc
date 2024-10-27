package com.grupo02.toctoc.repository.rest.pocketbase.login;

import com.grupo02.toctoc.models.dto.LoginPBDTO;

import java.util.Optional;

public interface LoginPBRepository {
    Optional<LoginPBDTO> execute(String user, String password);
}
