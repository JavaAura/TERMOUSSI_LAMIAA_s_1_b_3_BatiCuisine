package metier;

import java.util.List;

public class Projet {
	
	  	private int id;
	    private String nomProjet;
	    private double margeBeneficiaire;
	    private double coutTotal;
	    private EtatProjet etatProjet;  
	    private int idClient;  
	    
	 
	    
	    public Projet() {
	    }

	    public Projet( String nomProjet, double margeBeneficiaire, double coutTotal, EtatProjet etatProjet, int idClient) {
	        this.nomProjet = nomProjet;
	        this.margeBeneficiaire = margeBeneficiaire;
	        this.coutTotal = coutTotal;
	        this.etatProjet = etatProjet;
	        this.idClient=idClient;
	    }
	    
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getNomProjet() {
	        return nomProjet;
	    }

	    public void setNomProjet(String nomProjet) {
	        this.nomProjet = nomProjet;
	    }

	    public double getMargeBeneficiaire() {
	        return margeBeneficiaire;
	    }

	    public void setMargeBeneficiaire(double margeBeneficiaire) {
	        this.margeBeneficiaire = margeBeneficiaire;
	    }

	    public double getCoutTotal() {
	        return coutTotal;
	    }

	    public void setCoutTotal(double coutTotal) {
	        this.coutTotal = coutTotal;
	    }

	    public EtatProjet getEtatProjet() {
	        return etatProjet;
	    }

	    public void setEtatProjet(EtatProjet etatProjet) {
	        this.etatProjet = etatProjet;
	    }
	    
		public int getIdClient() {
			return idClient;
		}

		public void setIdClient(int idClient) {
			this.idClient = idClient;
		}

	    @Override
	    public String toString() {
	        return "Projet{" +
	                "id=" + id +
	                ", nomProjet='" + nomProjet + '\'' +
	                ", margeBeneficiaire=" + margeBeneficiaire +
	                ", coutTotal=" + coutTotal +
	                ", etatProjet=" + etatProjet +
	                ", idClient=" + idClient +
	                '}';
	    }

	    public double calculateTotalCost(List<Materiau> materiaux, List<MainOeuvre> mainOeuvres, double tva, double margin,double remise) {
	    
	    	 double totalMaterialCostBeforeTVA  = 0;
	    	 double totalMainOeuvreCostBeforeTVA = 0;
	    	    
	    	    // Calculate total cost of materials
	    	    for (Materiau materiau : materiaux) {
	    	        double materialCost = (materiau.getQuantite() * materiau.getCoutUnitaire()) + materiau.getCoutTransport(); 
	    	        totalMaterialCostBeforeTVA += materialCost;
	    	    }
	    	    double totalMaterialCostWithTVA = totalMaterialCostBeforeTVA + (totalMaterialCostBeforeTVA * tva / 100);
	    	    
	    	    // Calculate total cost of labor
	    	    for (MainOeuvre mainOeuvre : mainOeuvres) {
	    	        double laborCost = mainOeuvre.getTauxHoraire() * mainOeuvre.getHeuresTravail(); 
	    	        totalMainOeuvreCostBeforeTVA += laborCost;
	    	    }
	    	    double totalMainOeuvreCostWithVAT = totalMainOeuvreCostBeforeTVA + (totalMainOeuvreCostBeforeTVA * tva / 100);
	    	 
	    	    
	    	    // Total costs before  margin
	    	    double totalCostBeforeMargin = totalMaterialCostWithTVA + totalMainOeuvreCostWithVAT;
	    	    // Calculate profit margin
	    	    double profitMarginAmount = totalCostBeforeMargin * (margin / 100);
	    	    // Calculate discount
	    	    double discountAmount = totalCostBeforeMargin * (remise / 100);
	    	    // Total final cost after applying the discount
	    	    double finalTotalCost = totalCostBeforeMargin + profitMarginAmount - discountAmount;


	    	 // Display results
	    	    System.out.println("--- Détail des Coûts ---");
	    	    System.out.println("1. Matériaux :");
	    	    for (Materiau materiau : materiaux) {
	    	        double materialCost = (materiau.getQuantite() * materiau.getCoutUnitaire()) + materiau.getCoutTransport();
	    	        System.out.printf("- %s : %.2f € (quantité : %.2f, coût unitaire : %.2f €/m², transport : %.2f €)%n", 
	    	            materiau.getNom(), materialCost, materiau.getQuantite(), materiau.getCoutUnitaire(), materiau.getCoutTransport());
	    	    }
	    	    System.out.printf("**Coût total des matériaux avant TVA : %.2f €**%n", totalMaterialCostBeforeTVA);
	    	    System.out.printf("**Coût total des matériaux avec TVA (%.0f%%) : %.2f €**%n", tva, totalMaterialCostWithTVA);

	    	    System.out.println("2. Main-d'œuvre :");
	    	    for (MainOeuvre mainOeuvre : mainOeuvres) {
	    	        double laborCost = mainOeuvre.getTauxHoraire() * mainOeuvre.getHeuresTravail();
	    	        System.out.printf("- %s : %.2f € (taux horaire : %.2f €/h, heures travaillées : %.2f h)%n", 
	    	            mainOeuvre.getNom(), laborCost, mainOeuvre.getTauxHoraire(), mainOeuvre.getHeuresTravail());
	    	    }
	    	    System.out.printf("**Coût total de la main-d'œuvre avant TVA : %.2f €**%n", totalMainOeuvreCostBeforeTVA);
	    	    System.out.printf("**Coût total de la main-d'œuvre avec TVA (%.0f%%) : %.2f €**%n", tva, totalMainOeuvreCostWithVAT);

	    	    System.out.printf("3. Coût total avant marge : %.2f €%n", totalCostBeforeMargin);
	    	    System.out.printf("4. Marge bénéficiaire (%.0f%%) : %.2f €%n", margin, profitMarginAmount);
	    	    System.out.printf("5. Montant de la remise (%.0f%%) : %.2f €%n", remise, discountAmount);
	    	    System.out.printf("**Coût total final du projet : %.2f €**%n", finalTotalCost);

	    	    return finalTotalCost;
	    }

	    
	
}
