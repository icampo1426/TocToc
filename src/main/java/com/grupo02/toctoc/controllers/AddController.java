package com.grupo02.toctoc.controllers;

//import com.grupo02.toctoc.models.Add;
//import com.grupo02.toctoc.services.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
@RestController
@RequestMapping("/adds")
public class AddController {

    @Autowired
    private AddService addService;


    // Obtener todos los anuncios
    @GetMapping("/all")
    public ResponseEntity<List<Add>> getAllAdds() {
        List<Add> adds = addService.getAllAdds();
        return ResponseEntity.ok(adds);
    }

    // Obtener un anuncio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Add> getAddById(@PathVariable Long id) {
        Optional<Add> add = addService.getAddById(id);
        return add.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un anuncio por su ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdd(@PathVariable Long id) {
        Optional<Add> addOptional = addService.getAddById(id);

        if (addOptional.isPresent()) {
            addService.deleteAddById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

 */
