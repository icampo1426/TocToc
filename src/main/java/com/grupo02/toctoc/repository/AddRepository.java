package com.grupo02.toctoc.repository;

import com.grupo02.toctoc.models.Add;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddRepository extends JpaRepository<Add, String> {
    // Métodos adicionales de búsqueda
}
