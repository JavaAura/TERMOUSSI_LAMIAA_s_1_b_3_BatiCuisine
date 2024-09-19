package metier;

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
	        this.setIdClient(idClient);
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
	                ", client=" + client +
	                '}';
	    }

	
}
