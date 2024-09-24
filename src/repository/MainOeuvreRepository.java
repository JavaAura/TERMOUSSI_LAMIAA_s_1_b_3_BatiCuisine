package repository;

import java.util.List;

import metier.MainOeuvre;

public interface MainOeuvreRepository {

    void save(MainOeuvre mainOeuvre);
    MainOeuvre findById(int id);
    void update(MainOeuvre mainOeuvre);
    void delete(int id);
    List<MainOeuvre> findAll() ;
}
