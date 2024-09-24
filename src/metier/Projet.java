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

	    public double calculateTotalCost(List<Materiau> materiaux, List<MainOeuvre> mainOeuvres, double tvaRate, double marginRate) {
	    
	    	 double totalMaterialsCost = 0;
	    	    double totalMainOeuvreCost = 0;
	    	    
	    	    // Calculate total cost of materials
	    	    for (Materiau materiau : materiaux) {
	    	        double materialCost = (materiau.getCoutUnitaire() * materiau.getQuantite()) + materiau.getCoutTransport();
	    	        double adjustedMaterialCost = materialCost * materiau.getCoefQualite();
	    	        totalMaterialsCost += adjustedMaterialCost;
	    	    }
	    	    
	    	    // Calculate total cost of labor
	    	    for (MainOeuvre mainOeuvre : mainOeuvres) {
	    	        double laborCost = (mainOeuvre.getTauxHoraire() * mainOeuvre.getHeuresTravail()) * mainOeuvre.getProductiviteOuvrier();
	    	        totalMainOeuvreCost += laborCost; 
	    	    }
	    	    
	    	    // Total costs before applying taxes and margin
	    	    double totalBeforeTaxes = totalMaterialsCost + totalMainOeuvreCost;

	    	    // Apply VAT (if applicable)
	    	    double totalWithTVA = totalBeforeTaxes;
	    	    if (tvaRate > 0) {
	    	        totalWithTVA += totalBeforeTaxes * (tvaRate / 100);
	    	    }

	    	    // Apply profit margin (if applicable)
	    	    double finalTotalCost = totalWithTVA;
	    	    if (marginRate > 0) {
	    	        finalTotalCost += totalWithTVA * (marginRate / 100);
	    	    }

	    	    // Print the detailed breakdown
	    	    System.out.println("--- Résultat du Calcul ---");
	    	    System.out.println("Nom du projet : " + this.getNomProjet());
	    	    System.out.println("Client : " + this.getIdClient());//TODO: son nom
	    	    System.out.println("--- Détail des Coûts ---");

	    	    System.out.println("1. Matériaux :");
	    	    for (Materiau materiau : materiaux) {
	    	        System.out.printf("- %s : %.2f € (quantité : %.2f, coût unitaire : %.2f €/unité, qualité : %.2f, transport : %.2f €)%n", 
	    	                materiau.getNom(), (materiau.getCoutUnitaire() * materiau.getQuantite()), materiau.getQuantite(), 
	    	                materiau.getCoutUnitaire(), materiau.getCoefQualite(), materiau.getCoutTransport());
	    	    }
	    	    System.out.printf("**Coût total des matériaux avant TVA : %.2f €**%n", totalMaterialsCost);
	    	    System.out.printf("**Coût total des matériaux avec TVA (%.2f%%) : %.2f €**%n", tvaRate, totalMaterialsCost * (1 + tvaRate / 100));

	    	    System.out.println("2. Main-d'œuvre :");
	    	    for (MainOeuvre mainOeuvre : mainOeuvres) {
	    	        System.out.printf("- %s : %.2f € (taux horaire : %.2f €/h, heures travaillées : %.2f h, productivité : %.2f)%n", 
	    	                mainOeuvre.getNom(), (mainOeuvre.getTauxHoraire() * mainOeuvre.getHeuresTravail()), 
	    	                mainOeuvre.getTauxHoraire(), mainOeuvre.getHeuresTravail(), mainOeuvre.getProductiviteOuvrier());
	    	    }
	    	    System.out.printf("**Coût total de la main-d'œuvre avant TVA : %.2f €**%n", totalMainOeuvreCost);
	    	    System.out.printf("**Coût total de la main-d'œuvre avec TVA (%.2f%%) : %.2f €**%n", tvaRate, totalMainOeuvreCost * (1 + tvaRate / 100));

	    	    System.out.printf("3. Coût total avant marge : %.2f €%n", totalBeforeTaxes);
	    	    System.out.printf("4. Marge bénéficiaire (%.2f%%) : %.2f €%n", marginRate, totalWithTVA * (marginRate / 100));
	    	//    System.out.printf("**Coût total final du projet : %.2f €**%n", finalTotalCost);

	    	    return finalTotalCost;
	    }
	    
	
}
