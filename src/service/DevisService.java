package service;

import metier.Devis;
import repository.DevisRepositoryImpl;

public class DevisService {
	
    private final DevisRepositoryImpl devisRepository;

    public DevisService() {
        this.devisRepository = new DevisRepositoryImpl();
    }

    public void createDevis(Devis devis) {
        devisRepository.save(devis);
    }

    public Devis getDevisById(int id) {
        return devisRepository.findById(id);
    }

    public void updateDevis(Devis devis) {
        devisRepository.update(devis);
    }

    public void deleteDevis(int id) {
        devisRepository.delete(id);
    }
    
    public void updateDevisAccepte(int devisId, boolean accepte) {
    	Devis devis = getDevisById(devisId); 
        if (devis != null) {
            devis.setAccepte(accepte); 
            devisRepository.updateAccepte(devis);
        } else {
            System.out.println("Devis not found with ID: " + devisId);
        }
    }
}
