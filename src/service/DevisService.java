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
}
