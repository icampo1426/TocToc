package com.grupo02.toctoc.repository.db;

import com.grupo02.toctoc.models.Advertising;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisingRepository extends JpaRepository<Advertising, Long> {
    // Métodos adicionales de búsqueda
}
