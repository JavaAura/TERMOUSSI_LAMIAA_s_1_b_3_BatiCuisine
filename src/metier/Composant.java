package metier;

public class Composant {
	
	private int id;
    private String nom;           
    private double tauxTVA;       
    private TypeComposant typeComposant;  
    private Projet projet;     

    public Composant(int id, String nom, double tauxTVA, TypeComposant typeComposant, Projet projet) {
        this.id = id;
        this.nom = nom;
        this.tauxTVA = tauxTVA;
        this.typeComposant = typeComposant;
        this.projet = projet;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    @Override
    public String toString() {
        return "Composant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", tauxTVA=" + tauxTVA +
                ", typeComposant=" + typeComposant +
                ", projet=" + projet +
                '}';
    }
}
