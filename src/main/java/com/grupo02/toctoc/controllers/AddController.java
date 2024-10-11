package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.services.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adds")
public class AddController {

    @Autowired
    private AddService addService;

    // Métodos RESTful, análogos a las especificaciones en OpenAPI 
     public ResponseEntity<Add> createAdd(@RequestBody Add add) {
        Add createdAdd = addService.saveAdd(add);
        return ResponseEntity.ok(createdAdd); }
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

    // Actualizar un anuncio existente
    @PutMapping("/update/{id}")
    public ResponseEntity<Add> updateAdd(@PathVariable Long id, @RequestBody Add updatedAdd) {
        Optional<Add> addOptional = addService.getAddById(id);

        if (addOptional.isPresent()) {
            Add add = addOptional.get();
            // Actualiza los campos necesarios
            add.setTitle(updatedAdd.getTitle());
            add.setDescription(updatedAdd.getDescription());
            add.setStartDate(updatedAdd.getStartDate());
            add.setEndDate(updatedAdd.getEndDate());
            // Otros campos si son necesarios...

            Add savedAdd = addService.saveAdd(add);
            return ResponseEntity.ok(savedAdd);
        } else {
            return ResponseEntity.notFound().build();
        }
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
