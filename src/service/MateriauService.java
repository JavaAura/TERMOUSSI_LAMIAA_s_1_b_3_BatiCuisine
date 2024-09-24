package service;

import java.util.List;
import java.util.Optional;

import metier.Client;
import metier.Materiau;
import metier.Projet;
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
	    
	    public Optional<List<Materiau>>  getAllMateriaux() {
	    	 List<Materiau>  materiaux=materiauRepository.findAll();
	    	 return Optional.of(materiaux);   
	    }

		public void updateMateriauTVA(Materiau materiau,double tva) {
			materiau.setTauxTVA(tva);
			materiauRepository.updateTVA(materiau);
			
		}
}
