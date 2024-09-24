package service;

import java.util.List;

import metier.Composant;
import repository.ComposantRepository;
import repository.ComposantRepositoryImpl;
import repository.ProjetRepository;
import repository.ProjetRepositoryImpl;

public class ComposantService {

	  private final ComposantRepository composantRepository;

	    public ComposantService() {
	        this.composantRepository = new ComposantRepositoryImpl();
	    }

	

}
