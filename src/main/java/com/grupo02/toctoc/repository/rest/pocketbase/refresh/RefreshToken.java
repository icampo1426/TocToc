package com.grupo02.toctoc.repository.rest.pocketbase.refresh;

import com.grupo02.toctoc.models.dto.LoginPBDTO;

import java.util.Optional;

public interface RefreshToken {
    Optional<LoginPBDTO> execute(String jwt);
}
