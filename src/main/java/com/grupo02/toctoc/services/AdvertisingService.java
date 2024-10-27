package com.grupo02.toctoc.services;

import com.grupo02.toctoc.models.Advertising;
import com.grupo02.toctoc.repository.db.AdvertisingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdvertisingService {

    @Autowired
    private AdvertisingRepository addRepository;

    public List<Advertising> getAllAdds() {
        return addRepository.findAll();
    }

    // Obtener un anuncio por su ID
    public Optional<Advertising> getAddById(Long id) {
        return addRepository.findById(id);
    }

    // Eliminar un anuncio por su ID
    public void deleteAddById(Long id) {
        addRepository.deleteById(id);
    }

    // Métodos de lógica empresarial
}
