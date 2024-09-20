package presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import metier.Client;
import metier.MainOeuvre;
import metier.Materiau;
import metier.Projet;
import repository.ClientRepository;
import repository.ComposantRepository;
import repository.DevisRepository;
import repository.MainOeuvreRepository;
import repository.MateriauRepository;
import repository.ProjetRepository;

public class ConsoleUI {

		private Scanner scanner;
	    private ClientRepository clientRepository;
	    private MateriauRepository materiauRepository;
	    private MainOeuvreRepository mainOeuvreRepository;
	    private DevisRepository devisRepository;      
	    private ProjetRepository projetRepository;      
	    private ComposantRepository composantRepository; 
	
	    public ConsoleUI(ClientRepository clientRepository, 
	                     MateriauRepository materiauRepository, 
	                     MainOeuvreRepository mainOeuvreRepository, 
	                     DevisRepository devisRepository, 
	                     ProjetRepository projetRepository, 
	                     ComposantRepository composantRepository) {
	        this.scanner = new Scanner(System.in);
	        this.clientRepository = clientRepository;
	        this.materiauRepository = materiauRepository;
	        this.mainOeuvreRepository = mainOeuvreRepository;
	        this.devisRepository = devisRepository;       
	        this.projetRepository = projetRepository;      
	        this.composantRepository = composantRepository; 
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
		// TODO Auto-generated method stub
		
	}

	private void displayExistingProjects() {
		// TODO Auto-generated method stub
		
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

		    if (choice == 1) {
		        System.out.println("--- Recherche de client existant ---");
		        System.out.print("Entrez l'email du client : ");
		        String email = scanner.nextLine();

		        clientOptional = clientRepository.findByEmail(email);

		        if (clientOptional.isPresent()) {
		            Client client = clientOptional.get();
		            System.out.println("Client trouvé !");
		            System.out.println(client.toString());
		            System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
		            String continueClient = scanner.nextLine();
		            if (continueClient.equalsIgnoreCase("n")) {
		                return;
		            }
		        } else {
		            System.out.println("Client non trouvé.");
		            return;
		        }
		    }else if (choice == 2) {
		        System.out.println("--- Ajout d'un nouveau client ---");
		        clientOptional = createNewClient();
		    }
		    Client client = clientOptional.orElseThrow(() -> new RuntimeException("Client non disponible."));
		    // --- Création d'un Nouveau Projet ---
		    System.out.println("--- Création d'un Nouveau Projet ---");
		    System.out.print("Entrez le nom du projet : ");
		    String projectName = scanner.nextLine();

		    System.out.print("Entrez la surface de la cuisine (en m²) : ");
		    double surface = scanner.nextDouble();
		    scanner.nextLine(); 

		    // --- Ajout des matériaux ---
		    List<Materiau> materiaux = new ArrayList<>();
		    boolean addMoreMaterials = true;
		    while (addMoreMaterials) {
		        System.out.println("--- Ajout des matériaux ---");
		        System.out.print("Entrez le nom du matériau : ");
		        String materialName = scanner.nextLine();

		        System.out.print("Entrez la quantité de ce matériau : ");
		        double quantity = scanner.nextDouble();

		        System.out.print("Entrez le coût unitaire de ce matériau (MAD/unité) : ");
		        double unitCost = scanner.nextDouble();

		        System.out.print("Entrez le coût de transport de ce matériau (MAD) : ");
		        double transportCost = scanner.nextDouble();

		        System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
		        double qualityFactor = scanner.nextDouble();
		        scanner.nextLine();

		        Materiau materiau = new Materiau(materialName, tauxTVA, idProjet, unitCost, quantity, transportCost, qualityFactor);
		        materiaux.add(materiau);

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
		        System.out.print("Entrez le type de main-d'œuvre (e.g.,de_base, Specialiste) : ");
		        String mainOeuvreType = scanner.nextLine();

		        System.out.print("Entrez le taux horaire de cette main-d'œuvre (MAD/h) : ");
		        double hourlyRate = scanner.nextDouble();

		        System.out.print("Entrez le nombre d'heures travaillées : ");
		        double hoursWorked = scanner.nextDouble();

		        System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
		        double productivityFactor = scanner.nextDouble();
		        scanner.nextLine(); 

		        MainOeuvre mainOeuvre = new MainOeuvre(nom, tauxTVA, idProjet, hourlyRate, hoursWorked, productivityFactor, mainOeuvreType);
		        mainOeuvres.add(mainOeuvre);

		        System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
		        String addMainOeuvre = scanner.nextLine();
		        if (addMainOeuvre.equalsIgnoreCase("n")) {
		        	addMoremainOeuvres = false;
		        }
		    }
		    // --- Calcul du coût total ---
		    System.out.println("--- Calcul du coût total ---");
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
		    // Calculating costs
		    Projet projet = new Projet(projectName, margeBeneficiaire, coutTotal, etatProjet, idClient);		    double totalCost = projet.calculateTotalCost(tva, margin);

	}
}
