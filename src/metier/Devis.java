package metier;

import java.time.LocalDate;

public class Devis {
	
	 	private int id;
	    private double montantEstime;  
	    private LocalDate dateEmission;  
	    private LocalDate dateValidite; 
	    private boolean accepte;        
	    private int idProjet;        

	    public Devis() {
	    	
	    }
	    public Devis(double montantEstime, LocalDate dateEmission, LocalDate dateValidite, boolean accepte, int idProjet) {
	        this.montantEstime = montantEstime;
	        this.dateEmission = dateEmission;
	        this.dateValidite = dateValidite;
	        this.accepte = accepte;
	        this.setIdProjet(idProjet);
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

		public int getIdProjet() {
			return idProjet;
		}
		public void setIdProjet(int idProjet) {
			this.idProjet = idProjet;
		}
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
	
