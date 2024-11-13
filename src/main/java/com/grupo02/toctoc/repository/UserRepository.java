package com.grupo02.toctoc.repository;
import com.grupo02.toctoc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos adicionales de búsqueda
}