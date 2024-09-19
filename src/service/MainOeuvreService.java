package service;

import repository.MainOeuvreRepositoryImpl;

public class MainOeuvreService {
	

    private final MainOeuvreRepositoryImpl mainOeuvreRepository;

    public MainOeuvreService() {
        this.mainOeuvreRepository = new MainOeuvreRepositoryImpl();
    }

    // Create a new MainOeuvre
    public void createMainOeuvre(MainOeuvre mainOeuvre) {
        mainOeuvreRepository.save(mainOeuvre);
    }

    // Find a MainOeuvre by ID
    public MainOeuvre getMainOeuvreById(int id) {
        return mainOeuvreRepository.findById(id);
    }

    // Update an existing MainOeuvre
    public void updateMainOeuvre(MainOeuvre mainOeuvre) {
        mainOeuvreRepository.update(mainOeuvre);
    }

    // Delete a MainOeuvre by ID
    public void deleteMainOeuvre(int id) {
        mainOeuvreRepository.delete(id);
    }}
}
