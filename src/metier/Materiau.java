package metier;

public class Materiau extends Composant {
	
		private double coutUnitaire;    
	    private double quantite;        
	    private double coutTransport;  
	    private double coefQualite;     

	    public Materiau(int id, String nom, double tauxTVA, Projet projet, double coutUnitaire, double quantite, double coutTransport, double coefQualite) {
	        super(id, nom, tauxTVA, TypeComposant.MATERIAU, projet);
	        this.coutUnitaire = coutUnitaire;
	        this.quantite = quantite;
	        this.coutTransport = coutTransport;
	        this.coefQualite = coefQualite;
	    }
	    
	    public double getCoutUnitaire() {
	        return coutUnitaire;
	    }

	    public void setCoutUnitaire(double coutUnitaire) {
	        this.coutUnitaire = coutUnitaire;
	    }

	    public double getQuantite() {
	        return quantite;
	    }

	    public void setQuantite(double quantite) {
	        this.quantite = quantite;
	    }

	    public double getCoutTransport() {
	        return coutTransport;
	    }

	    public void setCoutTransport(double coutTransport) {
	        this.coutTransport = coutTransport;
	    }

	    public double getCoefQualite() {
	        return coefQualite;
	    }

	    public void setCoefQualite(double coefQualite) {
	        this.coefQualite = coefQualite;
	    }

	    @Override
	    public String toString() {
	        return "Materiau{" +
	                "coutUnitaire=" + coutUnitaire +
	                ", quantite=" + quantite +
	                ", coutTransport=" + coutTransport +
	                ", coefQualite=" + coefQualite +
	                "} " + super.toString();
	    }
}
