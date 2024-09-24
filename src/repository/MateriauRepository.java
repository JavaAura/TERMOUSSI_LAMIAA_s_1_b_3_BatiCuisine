package repository;

import java.util.List;

import metier.Materiau;

public interface MateriauRepository {
	
	void save(Materiau materiau);
    Materiau findById(int id);
    void update(Materiau materiau);
    void delete(int id);
    List<Materiau> findAll() ;
}
