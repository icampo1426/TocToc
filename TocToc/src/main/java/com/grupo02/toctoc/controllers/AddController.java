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

    // Crear un nuevo anuncio
    @PostMapping("/create")
    public ResponseEntity<Add> createAdd(@RequestBody Add add) {
        Add newAdd = addService.createAdd(add);
        return ResponseEntity.ok(newAdd);
    }

    // Actualizar un anuncio existente
    @PutMapping("/update/{id}")
    public ResponseEntity<Add> updateAdd(@PathVariable Long id, @RequestBody Add addDetails) {
        Optional<Add> updatedAdd = addService.updateAdd(id, addDetails);
        return updatedAdd.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

