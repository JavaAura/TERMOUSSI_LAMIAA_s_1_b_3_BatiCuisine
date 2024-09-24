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
import util.InputValidation;

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
		    String projectName="";
	        do {
	            System.out.print("Entrez le nom du projet : ");
	            projectName = scanner.nextLine();
	            if (!InputValidation.validateString(projectName)) {
	                System.out.println("Erreur : Le nom du projet ne peut pas être vide. Veuillez réessayer.");
	            }
	        } while (!InputValidation.validateString(projectName));
		    Optional<List<Projet>> projetsOptional = projetService.getAllProjets();		    
		    if (projetsOptional.isPresent()) {
		        List<Projet> projets = projetsOptional.get();
		        final String finalProjectName = projectName;
		        Optional<Projet> projetOptional = projets.stream()
		                .filter(projet -> projet.getNomProjet().equalsIgnoreCase(finalProjectName))
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
		                
		                String applyTVA;
		                do {
		                    System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
		                    applyTVA = scanner.nextLine();
		                    
		                    if (!InputValidation.validateYesOrNo(applyTVA)) {
		                        System.out.println("Erreur : Veuillez entrer 'y' pour oui ou 'n' pour non.");
		                    }
		                } while (!InputValidation.validateYesOrNo(applyTVA));
		    		    double tva = 0;
		    		    if (applyTVA.equalsIgnoreCase("y")) {
		    		        System.out.print("Entrez le pourcentage de TVA (%) : ");
		    		        String input;
		    		        do {
		    		            System.out.print("Entrez le pourcentage de TVA (%) : ");
		    		            input = scanner.nextLine();
		    		            
		    		            if (InputValidation.validateDouble(input)) {
		    		                tva = Double.parseDouble(input);
		    		            } else {
		    		                System.out.println("Entrée invalide. Veuillez entrer un nombre valide pour le pourcentage de TVA.");
		    		            }
		    		        } while (!InputValidation.validateDouble(input));
		                    // Update the tva for materials
		                    for (Materiau materiau : materiaux) {
		                        materiau.setTauxTVA(tva);
		                        materiauService.updateMateriauTVA(materiau,tva);
		                    }
		                    // Update the tva for labor
		                    for (MainOeuvre mainOeuvre : mainOeuvres) {
		                        mainOeuvreService.updateMainOeuvreTVA(mainOeuvre,tva);
		                    }
		                    System.out.println("TVA mise à jour avec succès pour les composants du projet : " + selectedProjet.getNomProjet());
		                }

		    		    String applyMargin;
		    		    do {
		    		        System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
		    		        applyMargin = scanner.nextLine();
		    		        
		    		        if (!InputValidation.validateYesOrNo(applyMargin)) {
		    		            System.out.println("Erreur : Veuillez entrer 'y' pour oui ou 'n' pour non.");
		    		        }
		    		    } while (!InputValidation.validateYesOrNo(applyMargin));
		    		    double margin = 0;
		    		    if (applyMargin.equalsIgnoreCase("y")) {
		    		    	String marginInput;
		    		        do {
		    		            System.out.print("Entrez le pourcentage de marge bénéficiaire (%) : ");
		    		            marginInput = scanner.nextLine();

		    		            if (InputValidation.validateDouble(marginInput)) {
		    		                margin = Double.parseDouble(marginInput);
		    		            } else {
		    		                System.out.println("Entrée invalide pour la marge bénéficiaire. Veuillez entrer un nombre valide.");
		    		            }
		    		        } while (!InputValidation.validateDouble(marginInput));
		    		       
		    		    }
		    		    
		    		    String applyRemise;
		    		    do {
		    		        System.out.print("Souhaitez-vous appliquer une remise au projet ? (y/n) : ");
		    		        applyRemise = scanner.nextLine();
		    		        
		    		        if (!InputValidation.validateYesOrNo(applyRemise)) {
		    		            System.out.println("Erreur : Veuillez entrer 'y' pour oui ou 'n' pour non.");
		    		        }
		    		    } while (!InputValidation.validateYesOrNo(applyRemise));
		    		    
		    		    double remise = 0;
		    		    if (applyRemise.equalsIgnoreCase("y")) {
		    		    	String remiseInput;
		    		        do {
		    		            System.out.print("Entrez le pourcentage de remise (%) : ");
		    		            remiseInput = scanner.nextLine();

		    		            if (InputValidation.validateDouble(remiseInput)) {
		    		            	remise = Double.parseDouble(remiseInput);
		    		            } else {
		    		                System.out.println("Entrée invalide pour la remise. Veuillez entrer un nombre valide.");
		    		            }
		    		        } while (!InputValidation.validateDouble(remiseInput));
		    		       
		    		    }
		    		    
		                double totalCost = selectedProjet.calculateTotalCost(materiaux, mainOeuvres, tva, margin,remise);
		                System.out.printf("Le coût total du projet '%s' est de : %.2f €%n", selectedProjet.getNomProjet(), totalCost);
		                //Enregistrement du Devis
		                System.out.println("--- Enregistrement du Devis ---");
					    String validDateStr;
				        while (true) {
				            System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
				            validDateStr = scanner.nextLine();
				            if (!InputValidation.validateDate(validDateStr)) {
				                System.out.println("Date invalide ou pas après aujourd'hui. Veuillez respecter le format jj/mm/aaaa.");
				            } else {
				                break;
				            }
				        }
				        
					    LocalDate issueDate = LocalDate.now();
					    LocalDate validDate = LocalDate.parse(validDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					    double montantEstime=totalCost;
					    Devis devis = new Devis(montantEstime, issueDate, validDate, false, selectedProjet.getId()); 
					    devisService.createDevis(devis);
					    //TODO: affiche details devis
					    String accepte;
					    do {
					        System.out.print("Est-ce que vous acceptez le projet ? (y/n) : ");
					        accepte = scanner.nextLine();
					        
					        if (!InputValidation.validateYesOrNo(accepte)) {
					            System.out.println("Erreur : Veuillez entrer 'y' pour oui ou 'n' pour non.");
					        }
					    } while (!InputValidation.validateYesOrNo(accepte));
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
		        String email;
		        do {
		            System.out.print("Entrez l'email du client : ");
		            email = scanner.nextLine();
		            
		            if (!InputValidation.validateEmail(email)) {
		                System.out.println("Erreur : L'email n'est pas valide. Veuillez réessayer.");
		            }
		        } while (!InputValidation.validateEmail(email));

		        clientOptional = clientService.getClientByEmail(email);

		        if (clientOptional.isPresent()) {
		            Client client = clientOptional.get();
		            System.out.println("Client trouvé !");
		            System.out.println(client.toString());
		            clientId = client.getId();
		            String continueClient;
		            do {
		                System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
		                continueClient = scanner.nextLine();
		                
		                if (!InputValidation.validateYesOrNo(continueClient)) {
		                    System.out.println("Erreur : Veuillez entrer 'y' pour oui ou 'n' pour non.");
		                }
		            } while (!InputValidation.validateYesOrNo(continueClient));
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
		        String nom;
		        do {
		            System.out.print("Entrez le nom du client: ");
		            nom = scanner.nextLine();

		            if (!InputValidation.validateString(nom)) {
		                System.out.println("Erreur : Le nom du client ne peut pas être vide. Veuillez réessayer.");
		            }
		        } while (!InputValidation.validateString(nom));

		        String adresse;
		        do {
		            System.out.print("Entrez l'adresse du client: ");
		            adresse = scanner.nextLine();

		            if (!InputValidation.validateString(adresse)) {
		                System.out.println("Erreur : L'adresse du client ne peut pas être vide. Veuillez réessayer.");
		            }
		        } while (!InputValidation.validateString(adresse));

		        String email;
		        do {
		            System.out.print("Entrez l'email du client: ");
		            email = scanner.nextLine();

		            if (!InputValidation.validateEmail(email)) {
		                System.out.println("Erreur : Veuillez entrer une adresse e-mail valide.");
		            }
		        } while (!InputValidation.validateEmail(email));
		        
		        String tel;
		        do {
		            System.out.print("Entrez le numéro de téléphone du client: ");
		            tel = scanner.nextLine();

		            if (!InputValidation.validatePhoneNumber(tel)) {
		                System.out.println("Erreur : Le numéro de téléphone doit contenir exactement 10 chiffres.");
		            }
		        } while (!InputValidation.validatePhoneNumber(tel));

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
		    String projectName;
	        do {
	            System.out.print("Entrez le nom du projet : ");
	            projectName = scanner.nextLine();

	            if (!InputValidation.validateString(projectName)) {
	                System.out.println("Erreur : Le nom du projet ne peut pas être vide. Veuillez réessayer.");
	            }
	        } while (!InputValidation.validateString(projectName));

	        String surfaceInput;
	        double surface = 0;
	        boolean validInput = false;

	        do {
	            System.out.print("Entrez la surface de la cuisine (en m²) : ");
	            surfaceInput = scanner.nextLine();

	            if (InputValidation.validateDouble(surfaceInput)) {
	                surface = Double.parseDouble(surfaceInput);
	                validInput = true; 
	            } else {
	                System.out.println("Erreur : Veuillez entrer un nombre valide pour la surface.");
	            }
	        } while (!validInput);
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
		        String materialName = "";
		        boolean validateInput = false;

		        do {
		            System.out.print("Entrez le nom du matériau : ");
		            materialName = scanner.nextLine();

		            if (InputValidation.validateString(materialName)) {
		            	validateInput = true; 
		            } else {
		                System.out.println("Erreur : Le nom du matériau ne peut pas être vide. Veuillez réessayer.");
		            }
		        } while (!validateInput);

		        double quantity = 0;
		        boolean validQuantity = false;
		        do {
		            System.out.print("Entrez la quantité de ce matériau : ");
		            String quantityInput = scanner.nextLine();
		            if (InputValidation.validateDouble(quantityInput)) {
		                quantity = Double.parseDouble(quantityInput);
		                validQuantity = true; 
		            } else {
		                System.out.println("Erreur : Veuillez entrer un nombre valide pour la quantité.");
		            }
		        } while (!validQuantity);

		        double unitCost = 0;
		        boolean validUnitCost = false;
		        do {
		            System.out.print("Entrez le coût unitaire de ce matériau (MAD/unité) : ");
		            String unitCostInput = scanner.nextLine();
		            if (InputValidation.validateDouble(unitCostInput)) {
		                unitCost = Double.parseDouble(unitCostInput);
		                validUnitCost = true; 
		            } else {
		                System.out.println("Erreur : Veuillez entrer un nombre valide pour le coût unitaire.");
		            }
		        } while (!validUnitCost);
		        double transportCost = 0;
		        boolean validTransportCost = false;
		        do {
		            System.out.print("Entrez le coût de transport de ce matériau (MAD) : ");
		            String transportCostInput = scanner.nextLine();
		            if (InputValidation.validateDouble(transportCostInput)) {
		                transportCost = Double.parseDouble(transportCostInput);
		                validTransportCost = true; 
		            } else {
		                System.out.println("Erreur : Veuillez entrer un nombre valide pour le coût de transport.");
		            }
		        } while (!validTransportCost);
		        double qualityFactor = 0;
		        boolean validQualityFactor = false;
		        do {
		            System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, 1.1 = haute qualité) : ");
		            String qualityFactorInput = scanner.nextLine(); 
		            if (InputValidation.validateFactor(qualityFactorInput)) { 
		                qualityFactor = Double.parseDouble(qualityFactorInput); 
		                validQualityFactor = true; 
		            } else {
		                System.out.println("Erreur : Veuillez entrer 1.0 ou 1.1 comme coefficient de qualité.");
		            }
		        } while (!validQualityFactor);
		       
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
		        String mainOeuvreName = "";
		        boolean validMainOeuvreName = false;
		        do {
		            System.out.print("Entrez le nom du main-d'œuvre : ");
		            mainOeuvreName = scanner.nextLine();
		            if (InputValidation.validateString(mainOeuvreName)) {
		                validMainOeuvreName = true; 
		            } else {
		                System.out.println("Erreur : Le nom ne peut pas être vide. Veuillez réessayer.");
		            }
		        } while (!validMainOeuvreName);

		        String mainOeuvreTypeInput;
		        boolean validMainOeuvreType = false;
		        do {
		            System.out.print("Entrez le type de main-d'œuvre (e.g., DE_BASE, SPECIALISTE) : ");
		            mainOeuvreTypeInput = scanner.nextLine().toUpperCase(); 
		            if (InputValidation.validateMainOeuvreType(mainOeuvreTypeInput)) {
		                validMainOeuvreType = true; 
		            } else {
		                System.out.println("Erreur : Le type doit être 'DE_BASE' ou 'SPECIALISTE'. Veuillez réessayer.");
		            }
		        } while (!validMainOeuvreType);
		      
		        double hourlyRate = 0;
		        boolean validHourlyRate = false;
		        do {
		            System.out.print("Entrez le taux horaire de cette main-d'œuvre (MAD/h) : ");
		            String hourlyRateInput = scanner.nextLine(); 
		            if (InputValidation.validateDouble(hourlyRateInput)) {
		                hourlyRate = Double.parseDouble(hourlyRateInput); 
		                validHourlyRate = true; 
		            } else {
		                System.out.println("Erreur : Veuillez entrer un nombre valide pour le taux horaire.");
		            }
		        } while (!validHourlyRate);

		        double hoursWorked = 0;
		        boolean validHoursWorked = false;
		        do {
		            System.out.print("Entrez le nombre d'heures travaillées : ");
		            String hoursWorkedInput = scanner.nextLine(); 
		            if (InputValidation.validateDouble(hoursWorkedInput)) {
		                hoursWorked = Double.parseDouble(hoursWorkedInput); 
		                validHoursWorked = true; 
		            } else {
		                System.out.println("Erreur : Veuillez entrer un nombre valide pour le nombre d'heures.");
		            }
		        } while (!validHoursWorked);
		        double productivityFactor = 0;
		        boolean validProductivityFactor = false;
		        do {
		            System.out.print("Entrez le facteur de productivité (1.0 = standard, 1.1 = haute productivité) : ");
		            String productivityInput = scanner.nextLine(); 
		            if (InputValidation.validateFactor(productivityInput)) {
		                productivityFactor = Double.parseDouble(productivityInput); 
		                validProductivityFactor = true; 
		            } else {
		                System.out.println("Erreur : Veuillez entrer 1.0 ou 1.1 comme facteur de productivité.");
		            }
		        } while (!validProductivityFactor);
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
		       
		        String addMainOeuvre;
		        do {
		            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
		            addMainOeuvre = scanner.nextLine(); 

		            if (!InputValidation.validateYesOrNo(addMainOeuvre)) { 
		                System.out.println("Erreur : Veuillez entrer 'y' pour oui ou 'n' pour non."); 
		            }
		        } while (!InputValidation.validateYesOrNo(addMainOeuvre));
		        if (addMainOeuvre.equalsIgnoreCase("n")) {
		        	addMoremainOeuvres = false;
		        }
		    }		    
	}
}
