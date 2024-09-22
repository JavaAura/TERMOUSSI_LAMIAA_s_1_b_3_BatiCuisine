package presentation;

import java.sql.SQLException;

import service.*; 
import database.DbConnection;

public class Main {

	public static void main(String[] args) {
		
		   ClientService clientService = new ClientService(); 
	        MateriauService materiauService = new MateriauService();
	        MainOeuvreService mainOeuvreService = new MainOeuvreService();
	        DevisService devisService = new DevisService();
	        ProjetService projetService = new ProjetService();
	        ComposantService composantService = new ComposantService();

	        ConsoleUI consoleUI = new ConsoleUI(clientService, 
	                                            materiauService, 
	                                            mainOeuvreService, 
	                                            devisService, 
	                                            projetService, 
	                                            composantService);

        consoleUI.start();
	
	}

}
