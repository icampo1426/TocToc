package com.grupo02.toctoc.repository;

import com.grupo02.toctoc.models.Add;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddRepository extends JpaRepository<Add, Long> {
    // Aquí puedes definir métodos de consulta personalizados si los necesitas,
    // pero JpaRepository ya te provee los métodos CRUD básicos como:
    // - save(Add entity)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // Puedes añadir consultas personalizadas si es necesario.
}
