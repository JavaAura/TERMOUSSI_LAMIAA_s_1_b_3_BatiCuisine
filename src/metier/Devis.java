package metier;

import java.time.LocalDate;

public class Devis {
	
	 	private int id;
	    private double montantEstime;  
	    private LocalDate dateEmission;  
	    private LocalDate dateValidite; 
	    private boolean accepte;        
	    private Projet projet;        

	    public Devis( double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, Projet projet) {
	        this.montantEstime = montantEstime;
	        this.dateEmission = dateEmission;
	        this.dateValidite = dateValidite;
	        this.accepte = accepte;
	        this.projet = projet;
	    }
	    
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public double getMontantEstime() {
	        return montantEstime;
	    }

	    public void setMontantEstime(double montantEstime) {
	        this.montantEstime = montantEstime;
	    }

	    public LocalDate getDateEmission() {
	        return dateEmission;
	    }

	    public void setDateEmission(LocalDate dateEmission) {
	        this.dateEmission = dateEmission;
	    }

	    public LocalDate getDateValidite() {
	        return dateValidite;
	    }

	    public void setDateValidite(LocalDate dateValidite) {
	        this.dateValidite = dateValidite;
	    }

	    public boolean isAccepte() {
	        return accepte;
	    }

	    public void setAccepte(boolean accepte) {
	        this.accepte = accepte;
	    }

	    public Projet getProjet() {
	        return projet;
	    }

	    public void setProjet(Projet projet) {
	        this.projet = projet;
	    }

	    @Override
	    public String toString() {
	        return "Devis{" +
	                "id=" + id +
	                ", montantEstime=" + montantEstime +
	                ", dateEmission=" + dateEmission +
	                ", dateValidite=" + dateValidite +
	                ", accepte=" + accepte +
	                ", projet=" + projet +
	                '}';
	    }
}
