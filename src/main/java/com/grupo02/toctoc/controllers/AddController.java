package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.models.Advertising;
import com.grupo02.toctoc.services.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/advertisement")
public class AddController {

    @Autowired
    private AdvertisingService advertisingService;


    // Obtener todos los anuncios
    @GetMapping("/")
    public ResponseEntity<List<Advertising>> getAllAdds() {
        List<Advertising> adds = advertisingService.getAllAdds();
        return ResponseEntity.ok(adds);
    }

    // Obtener un anuncio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Advertising> getAddById(@PathVariable Long id) {
        Optional<Advertising> add = advertisingService.getAddById(id);
        return add.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un anuncio por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdd(@PathVariable Long id) {
        Optional<Advertising> addOptional = advertisingService.getAddById(id);
        if (addOptional.isPresent()) {
            advertisingService.deleteAddById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
