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

    // Crear un nuevo anuncio
    public Add createAdd(Add add) {
        return addRepository.save(add);
    }

    // Actualizar un anuncio
    public Optional<Add> updateAdd(Long id, Add addDetails) {
        return addRepository.findById(id).map(add -> {
            add.setTitle(addDetails.getTitle());
            add.setImage(addDetails.getImage());
            add.setLink(addDetails.getLink());
            add.setStartDate(addDetails.getStartDate());
            add.setEndDate(addDetails.getEndDate());
            return addRepository.save(add);
        });
    }

    // Eliminar un anuncio por su ID
    public void deleteAddById(Long id) {
        addRepository.deleteById(id);
    }
}
