package repository;

import java.util.List;

import metier.EtatProjet;
import metier.Projet;

public interface ProjetRepository {
	
	void save(Projet projet);
    void update(Projet projet);
    void delete(int projetId);
    Projet findById(int id);
    List<Projet> findAll();
	void updateEtat(Projet projet,EtatProjet nouvelEtat);
}
