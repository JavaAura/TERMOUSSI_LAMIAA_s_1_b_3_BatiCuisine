package service;


import java.util.Optional;

import metier.Projet;
import repository.ProjetRepository;
import repository.ProjetRepositoryImpl;

public class ProjetService {
	
	  private final ProjetRepository projetRepository;

	    public ProjetService() {
	        this.projetRepository = new ProjetRepositoryImpl();
	    }

	    public Optional<Projet> createProjet(Projet projet) {
	        projetRepository.save(projet);  
	        return Optional.of(projet);   
	    }
	    public void removeProjet(int projetId) {
	        projetRepository.delete(projetId);
	    }

	    public Projet getProjetById(int id) {
	        return projetRepository.findById(id);
	    }

	    public void updateProjet(Projet projet) {
	        projetRepository.update(projet);
	    }
}
