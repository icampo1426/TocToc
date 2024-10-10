package com.grupo02.toctoc.services;

import com.grupo02.toctoc.repository.AddRepository;
import org.springframework.stereotype.Service;

@Service
public class AddService {
    private final AddRepository addRepository;

    public AddService(AddRepository addRepository) {
        this.addRepository = addRepository;
    }

    // Métodos de lógica empresarial
}
