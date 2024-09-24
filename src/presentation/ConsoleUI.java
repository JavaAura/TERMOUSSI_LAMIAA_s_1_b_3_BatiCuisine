package presentation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import metier.Client;
import metier.Devis;
import metier.EtatProjet;
import metier.MainOeuvre;
import metier.Materiau;
import metier.Projet;
import metier.TypeMainOeuvre;
import service.*;

public class ConsoleUI {

	private Scanner scanner;
	private ClientService clientService;
	private MateriauService materiauService;
	private MainOeuvreService mainOeuvreService;
	private DevisService devisService;
	private ProjetService projetService;
	private ComposantService composantService;

	public ConsoleUI(ClientService clientService, 
	                 MateriauService materiauService, 
	                 MainOeuvreService mainOeuvreService, 
	                 DevisService devisService, 
	                 ProjetService projetService, 
	                 ComposantService composantService) {
	    this.scanner = new Scanner(System.in);
	    this.clientService = clientService;
	    this.materiauService = materiauService;
	    this.mainOeuvreService = mainOeuvreService;
	    this.devisService = devisService;
	    this.projetService = projetService;
	    this.composantService = composantService;
	}

    public void start() {
        System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
        mainMenu();
    }

    private void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Créer un nouveau projet");
            System.out.println("2. Afficher les projets existants");
            System.out.println("3. Calculer le coût d'un projet");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    createNewProject();
                    break;
                case 2:
                    displayExistingProjects();
                    break;
                case 3:
                    calculateProjectCost();
                    break;
                case 4:
                    running = false;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

	private void calculateProjectCost() {
			System.out.println("--- Sélection d'un projet pour calcul du coût ---");
		    System.out.print("Entrez le nom du projet : ");
		    String projectName = scanner.nextLine();
		    Optional<List<Projet>> projetsOptional = projetService.getAllProjets();		    
		    if (projetsOptional.isPresent()) {
		        List<Projet> projets = projetsOptional.get();

		        Optional<Projet> projetOptional = projets.stream()
		                .filter(projet -> projet.getNomProjet().equalsIgnoreCase(projectName))
		                .findFirst();
		        if (projetOptional.isPresent()) {
		            Projet selectedProjet = projetOptional.get();
		            System.out.println("Projet trouvé : " + selectedProjet.getNomProjet());
		            Optional<List<Materiau>> materiauxOptional = materiauService.getAllMateriaux();
		            Optional<List<MainOeuvre>> mainOeuvresOptional = mainOeuvreService.getAllMainOeuvres();
		            
		            if (materiauxOptional.isPresent() && mainOeuvresOptional.isPresent()) {
		                List<Materiau> allMateriaux = materiauxOptional.get();
		                List<Materiau> materiaux = allMateriaux.stream()
		                        .filter(materiau -> materiau.getIdProjet() == selectedProjet.getId())
		                        .collect(Collectors.toList());

		                List<MainOeuvre> allMainOeuvres = mainOeuvresOptional.get();
		                List<MainOeuvre> mainOeuvres = allMainOeuvres.stream()
		                        .filter(mainOeuvre -> mainOeuvre.getIdProjet() == selectedProjet.getId())
		                        .collect(Collectors.toList());
		                
		                System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
		    		    String applyTVA = scanner.nextLine();
		    		    double tva = 0;
		    		    if (applyTVA.equalsIgnoreCase("y")) {
		    		        System.out.print("Entrez le pourcentage de TVA (%) : ");
		    		        tva = scanner.nextDouble();
		    		        scanner.nextLine();
		    		    }

		    		    System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
		    		    String applyMargin = scanner.nextLine();
		    		    double margin = 0;
		    		    if (applyMargin.equalsIgnoreCase("y")) {
		    		        System.out.print("Entrez le pourcentage de marge bénéficiaire (%) : ");
		    		        margin = scanner.nextDouble();
		    		        scanner.nextLine();
		    		    }
		    		    
		                double totalCost = selectedProjet.calculateTotalCost(materiaux, mainOeuvres, tva, margin);
		                System.out.printf("Le coût total du projet '%s' est de : %.2f €%n", selectedProjet.getNomProjet(), totalCost);
		                //Enregistrement du Devis
		                System.out.println("--- Enregistrement du Devis ---");
					    System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
					    String validDateStr = scanner.nextLine();
				        
					    LocalDate issueDate = LocalDate.now();
					    LocalDate validDate = LocalDate.parse(validDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					    double montantEstime=totalCost;
					    Devis devis = new Devis(montantEstime, issueDate, validDate, false, selectedProjet.getId()); 
					    devisService.createDevis(devis);
					    //TODO: affiche details devis
					    System.out.print("Est ce que vous acceptez le projet ? (y/n) : ");
					    String accepte = scanner.nextLine();
					    if (accepte.equalsIgnoreCase("y")) {
					        devisService.updateDevisAccepte(devis.getId(), true);
					        projetService.updateProjetEtat(selectedProjet.getId(),EtatProjet.TERMINE);
					        System.out.println("Le projet a été accepté !");
					    }else {
					    	projetService.updateProjetEtat(selectedProjet.getId(),EtatProjet.ANNULE);
					    }
		           }else {
		                System.out.println("Matériaux ou main-d'œuvre introuvables pour ce projet.");
		            }
		        }else {
		            System.out.println("Projet non trouvé.");
		        }
		    } else {
		        System.out.println("Aucun projet disponible.");
		    }
		    
	}

	private void displayExistingProjects() {
	    System.out.println("--- Affichage des projets existants ---");
	    Optional<List<Projet>> projetsOptional = projetService.getAllProjets();
	    if (projetsOptional.isPresent()) {
	        List<Projet> projets = projetsOptional.get();
	        if (projets.isEmpty()) {
	            System.out.println("Aucun projet disponible.");
	        } else {
	            projets.forEach(projet -> 
	                System.out.printf("Nom: %s, État: %s, Client ID: %d%n", 
	                projet.getNomProjet(), projet.getEtatProjet(), projet.getIdClient()));
	        }
	    } else {
	        System.out.println("Erreur lors de la récupération des projets.");
	    }
		
	}

	private void createNewProject() {
		
		 System.out.println("--- Recherche de client ---");
		    System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
		    System.out.println("1. Chercher un client existant");
		    System.out.println("2. Ajouter un nouveau client");
		    System.out.print("Choisissez une option : ");

		    int choice = scanner.nextInt();
		    scanner.nextLine(); 

		    Optional<Client> clientOptional = Optional.empty();
			int clientId=0;
		    if (choice == 1) {
		    
		        System.out.println("--- Recherche de client existant ---");
		        System.out.print("Entrez l'email du client : ");
		        String email = scanner.nextLine();

		        clientOptional = clientService.getClientByEmail(email);

		        if (clientOptional.isPresent()) {
		            Client client = clientOptional.get();
		            System.out.println("Client trouvé !");
		            System.out.println(client.toString());
		            clientId = client.getId();
		            System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
		            String continueClient = scanner.nextLine();
		            if (continueClient.equalsIgnoreCase("n")) {
		                return;
		            }
		        } else {
		            System.out.println("Client non trouvé.");
		            return;
		        }
		        //Ajouter nouveau client
		    }else if (choice == 2) {
		        System.out.println("--- Ajout d'un nouveau client ---");
		        System.out.print("Entrez le nom du client: ");
		        String nom = scanner.nextLine();

		        System.out.print("Entrez l'adresse du client: ");
		        String adresse = scanner.nextLine();

		        System.out.print("Entrez l'email du client: ");
		        String email = scanner.nextLine();

		        System.out.print("Entrez le numéro de téléphone du client: ");
		        String tel= scanner.nextLine();
		        Client client=new Client(nom,adresse,email,tel);
		        clientOptional = clientService.createClient(client);
		        if (clientOptional.isPresent()) {
		            Client createdClient = clientOptional.get();
		            clientId = createdClient.getId(); 
		        }
		    }
		    Client client = clientOptional.orElseThrow(() -> new RuntimeException("Client non disponible."));
		    // --- Création d'un Nouveau Projet ---
		    System.out.println("--- Création d'un Nouveau Projet ---");
		    System.out.print("Entrez le nom du projet : ");
		    String projectName = scanner.nextLine();

		    System.out.print("Entrez la surface de la cuisine (en m²) : ");
		    double surface = scanner.nextDouble();
		    scanner.nextLine(); 
		    Projet projet = new Projet(projectName, 0, 0, EtatProjet.EN_COURS, clientId);	
		    Optional<Projet> projetOptional = projetService.createProjet(projet);
		    if (projetOptional.isPresent()) {
		        Projet createdProjet = projetOptional.get();
		        System.out.println("Projet créé avec succès : " + createdProjet.getNomProjet());
		    } else {
		        System.out.println("Échec de la création du projet.");
		    }
		        // --- Ajout des matériaux ---
		    List<Materiau> materiaux = new ArrayList<>();
		    boolean addMoreMaterials = true;
		    Projet createdProjet = projetOptional.get();
	        int projetId=createdProjet.getId();
		    while (addMoreMaterials) {
		        System.out.println("--- Ajout des matériaux ---");
		        System.out.print("Entrez le nom du matériau : ");
		        String materialName = scanner.nextLine();

		        System.out.print("Entrez la quantité de ce matériau : ");
		        double quantity = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Entrez le coût unitaire de ce matériau (MAD/unité) : ");
		        double unitCost = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Entrez le coût de transport de ce matériau (MAD) : ");
		        double transportCost = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
		        double qualityFactor = scanner.nextDouble();
		        scanner.nextLine();
		       
		        Materiau materiau = new Materiau(materialName, 0, projetId, unitCost, quantity, transportCost, qualityFactor);

		        Optional<Materiau> materiauOptional = materiauService.createMateriau(materiau);
		        if (materiauOptional.isPresent()) {
		            Materiau createdMateriau = materiauOptional.get();
		            materiaux.add(createdMateriau);
		            System.out.println("Matériau ajouté avec succès : " + createdMateriau.getNom());
		        } else {
		            System.out.println("Échec de l'ajout du matériau.");
		        }

		        System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");
		        String addMaterial = scanner.nextLine();
		        if (addMaterial.equalsIgnoreCase("n")) {
		            addMoreMaterials = false;
		        }
		    }
		    // --- Ajout de la main-d'œuvre ---
		    List<MainOeuvre> mainOeuvres = new ArrayList<>();
		    boolean addMoremainOeuvres = true;
		    while (addMoremainOeuvres) {
		        System.out.println("--- Ajout de la main-d'œuvre ---");
		        System.out.print("Entrez le nom du main-d'œuvre  : ");
		        String mainOeuvreName = scanner.nextLine();
		        System.out.print("Entrez le type de main-d'œuvre (e.g.,de_base, Specialiste) : ");
		        String mainOeuvreTypeInput  = scanner.nextLine().toUpperCase();
		      
		        System.out.print("Entrez le taux horaire de cette main-d'œuvre (MAD/h) : ");
		        double hourlyRate = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Entrez le nombre d'heures travaillées : ");
		        double hoursWorked = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
		        double productivityFactor = scanner.nextDouble();
		        scanner.nextLine(); 
		        try {
		        	 TypeMainOeuvre mainOeuvreType = TypeMainOeuvre.valueOf(mainOeuvreTypeInput);
		             
		             MainOeuvre mainOeuvre = new MainOeuvre(mainOeuvreName, 0, projetId, hourlyRate, hoursWorked, productivityFactor, mainOeuvreType);
		             
		             Optional<MainOeuvre> mainOeuvreOptional = mainOeuvreService.createMainOeuvre(mainOeuvre);
		            
		             if (mainOeuvreOptional.isPresent()) {
		                 MainOeuvre createdMainOeuvre = mainOeuvreOptional.get();
		                 mainOeuvres.add(createdMainOeuvre);
		                 System.out.println("Main-d'œuvre ajoutée avec succès : " + createdMainOeuvre.getNom());
		             } else {
		                 System.out.println("Échec de l'ajout de la main-d'œuvre.");
		             }
		        } catch (IllegalArgumentException e) {
		            System.out.println("Type de main-d'œuvre invalide. Veuillez entrer DE_BASE ou SPECIALISTE.");
		        }
		       
		        System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
		        String addMainOeuvre = scanner.nextLine();
		        if (addMainOeuvre.equalsIgnoreCase("n")) {
		        	addMoremainOeuvres = false;
		        }
		    }		    
	}
}
