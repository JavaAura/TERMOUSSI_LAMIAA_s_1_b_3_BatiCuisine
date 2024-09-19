package repository;

import metier.Projet;

public interface ProjetRepository {
	
	void save(Projet projet);
    void update(Projet projet);
    void delete(int projetId);
    Projet findById(int id);
}
