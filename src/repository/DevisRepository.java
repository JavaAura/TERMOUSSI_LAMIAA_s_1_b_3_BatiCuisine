package repository;

import metier.Devis;

public interface DevisRepository {
	
		void save(Devis devis);


	    Devis findById(int id);

	    void update(Devis devis);

	    void delete(int id);
	    void updateAccepte(Devis devis);
}
