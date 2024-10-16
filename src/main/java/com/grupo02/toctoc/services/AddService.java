package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.Add;
import com.grupo02.toctoc.repository.AddRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/*
@Service
public class AddService {
    private final AddRepository addRepository;

    public AddService(AddRepository addRepository) {
        this.addRepository = addRepository;
    }

    public List<Add> getAllAdds() {
        return addRepository.findAll();
    }

    // Obtener un anuncio por su ID
    public Optional<Add> getAddById(Long id) {
        return addRepository.findById(id);
    }

    // Eliminar un anuncio por su ID
    public void deleteAddById(Long id) {
        addRepository.deleteById(id);
    }

    // Métodos de lógica empresarial
}
*/