package service;

import metier.MainOeuvre;
import repository.MainOeuvreRepositoryImpl;

public class MainOeuvreService {
	

    private final MainOeuvreRepositoryImpl mainOeuvreRepository;

    public MainOeuvreService() {
        this.mainOeuvreRepository = new MainOeuvreRepositoryImpl();
    }

    public void createMainOeuvre(MainOeuvre mainOeuvre) {
        mainOeuvreRepository.save(mainOeuvre);
    }

    public MainOeuvre getMainOeuvreById(int id) {
        return mainOeuvreRepository.findById(id);
    }

    public void updateMainOeuvre(MainOeuvre mainOeuvre) {
        mainOeuvreRepository.update(mainOeuvre);
    }

    public void deleteMainOeuvre(int id) {
        mainOeuvreRepository.delete(id);
    }
}

