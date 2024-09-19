package service;

import metier.Materiau;
import repository.MateriauRepositoryImpl;

public class MateriauService {
	
	 private final MateriauRepositoryImpl materiauRepository;

	    public MateriauService() {
	        this.materiauRepository = new MateriauRepositoryImpl();
	    }

	    public void createMateriau(Materiau materiau) {
	        materiauRepository.save(materiau);
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
