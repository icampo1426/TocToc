package com.grupo02.toctoc.repository.db;
import com.grupo02.toctoc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdentityId(String identityId);
    // Métodos adicionales de búsqueda
}
