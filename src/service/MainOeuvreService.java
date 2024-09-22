package service;

import java.util.Optional;

import metier.MainOeuvre;
import metier.Materiau;
import repository.MainOeuvreRepositoryImpl;

public class MainOeuvreService {
	

    private final MainOeuvreRepositoryImpl mainOeuvreRepository;

    public MainOeuvreService() {
        this.mainOeuvreRepository = new MainOeuvreRepositoryImpl();
    }

    public Optional<MainOeuvre>  createMainOeuvre(MainOeuvre mainOeuvre) {
        mainOeuvreRepository.save(mainOeuvre);
        return Optional.of(mainOeuvre);
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

