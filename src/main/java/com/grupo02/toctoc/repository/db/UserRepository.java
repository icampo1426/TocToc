package com.grupo02.toctoc.repository.db;
import com.grupo02.toctoc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByIdentityId(String identityId);
    // Métodos adicionales de búsqueda
    Optional<User> findByEmail(String email);
    List<User> findAllByNameContaining(String name);
    List<User> findAllByLastnameContaining(String lastname);

}
