package repository;

import metier.Composant;

public interface ComposantRepository {
	
	 void save(Composant composant);
	    Composant findById(int id);
	    void update(Composant composant);
	    void delete(int id);
}
