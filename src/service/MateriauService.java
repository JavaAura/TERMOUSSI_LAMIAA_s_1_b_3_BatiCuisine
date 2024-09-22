package service;

import java.util.Optional;

import metier.Client;
import metier.Materiau;
import repository.MateriauRepositoryImpl;

public class MateriauService {
	
	 private final MateriauRepositoryImpl materiauRepository;

	    public MateriauService() {
	        this.materiauRepository = new MateriauRepositoryImpl();
	    }

	    public Optional<Materiau>  createMateriau(Materiau materiau) {
	        materiauRepository.save(materiau);
	        return Optional.of(materiau);
	    }
	   
	    public Materiau getMateriauById(int id) {
	        return materiauRepository.findById(id);
	    }

	    public void updateMateriau(Materiau materiau) {
	        materiauRepository.update(materiau);
	    }

	    public void deleteMateriau(int id) {
	        materiauRepository.delete(id);
	    }
}
