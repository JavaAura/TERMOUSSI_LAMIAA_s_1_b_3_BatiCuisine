package presentation;

import java.sql.SQLException;

import repository.*; 
import database.DbConnection;

public class Main {

	public static void main(String[] args) {
		
	    ClientRepository clientRepository = new ClientRepositoryImpl(); 
        MateriauRepository materiauRepository = new MateriauRepositoryImpl();
        MainOeuvreRepository mainOeuvreRepository = new MainOeuvreRepositoryImpl();
        DevisRepository devisRepository = new DevisRepositoryImpl();
        ProjetRepository projetRepository = new ProjetRepositoryImpl();
        ComposantRepository composantRepository = new ComposantRepositoryImpl();

        ConsoleUI consoleUI = new ConsoleUI(clientRepository, materiauRepository, mainOeuvreRepository, 
                                            devisRepository, projetRepository, composantRepository);

        consoleUI.start();
	
	}

}
