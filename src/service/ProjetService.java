package service;


import java.util.List;
import java.util.Optional;

import metier.EtatProjet;
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
	    
	    public Optional<List<Projet>>  getAllProjets() {
	    	 List<Projet>  projets=projetRepository.findAll();
	    	 return Optional.of(projets);   
	    }

		public void updateProjetEtat(int id, EtatProjet nouvelEtat) {
			 Projet projet = getProjetById(id); 
			    if (projet != null) {
			        projet.setEtatProjet(nouvelEtat); 
			        projetRepository.updateEtat(projet,nouvelEtat); 
			    } else {
			        System.out.println("Projet not found with ID: " + id);
			    }
			
		}
}
