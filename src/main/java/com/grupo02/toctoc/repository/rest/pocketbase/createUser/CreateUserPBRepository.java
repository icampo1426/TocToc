package com.grupo02.toctoc.repository.rest.pocketbase.createUser;

import com.grupo02.toctoc.models.dto.NewUserPBDTO;
import com.grupo02.toctoc.models.dto.UserPBDTO;

import java.util.Optional;

public interface CreateUserPBRepository {
    Optional<UserPBDTO> execute(NewUserPBDTO newUserPBDTO);
}
