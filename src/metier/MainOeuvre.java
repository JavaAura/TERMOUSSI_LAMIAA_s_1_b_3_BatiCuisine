package metier;

public class MainOeuvre extends Composant {
	
		private double tauxHoraire;          
	    private double heuresTravail;        
	    private double productiviteOuvrier;  
	    private TypeMainOeuvre typeMainOeuvre;  

	    public MainOeuvre(int id, String nom, double tauxTVA, Projet projet, double tauxHoraire, double heuresTravail, double productiviteOuvrier, TypeMainOeuvre typeMainOeuvre) {
	        super(id, nom, tauxTVA, TypeComposant.MAINOEUVRE, projet);
	        this.tauxHoraire = tauxHoraire;
	        this.heuresTravail = heuresTravail;
	        this.productiviteOuvrier = productiviteOuvrier;
	        this.typeMainOeuvre = typeMainOeuvre;
	    }
	    
	    public double getTauxHoraire() {
	        return tauxHoraire;
	    }

	    public void setTauxHoraire(double tauxHoraire) {
	        this.tauxHoraire = tauxHoraire;
	    }

	    public double getHeuresTravail() {
	        return heuresTravail;
	    }

	    public void setHeuresTravail(double heuresTravail) {
	        this.heuresTravail = heuresTravail;
	    }

	    public double getProductiviteOuvrier() {
	        return productiviteOuvrier;
	    }

	    public void setProductiviteOuvrier(double productiviteOuvrier) {
	        this.productiviteOuvrier = productiviteOuvrier;
	    }

	    public TypeMainOeuvre getTypeMainOeuvre() {
	        return typeMainOeuvre;
	    }

	    public void setTypeMainOeuvre(TypeMainOeuvre typeMainOeuvre) {
	        this.typeMainOeuvre = typeMainOeuvre;
	    }

	    @Override
	    public String toString() {
	        return "MainOeuvre{" +
	                "tauxHoraire=" + tauxHoraire +
	                ", heuresTravail=" + heuresTravail +
	                ", productiviteOuvrier=" + productiviteOuvrier +
	                ", typeMainOeuvre=" + typeMainOeuvre +
	                "} " + super.toString();
	    }

}
