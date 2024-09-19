package repository;

import metier.MainOeuvre;

public interface MainOeuvreRepository {

    void save(MainOeuvre mainOeuvre);
    MainOeuvre findById(int id);
    void update(MainOeuvre mainOeuvre);
    void delete(int id);
}
