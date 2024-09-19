package metier;

public class Composant {
	
	private int id;
    private String nom;           
    private double tauxTVA;       
    private TypeComposant typeComposant;  
    private int idProjet;     

    public Composant( String nom, double tauxTVA, TypeComposant typeComposant, int idProjet) {
        this.nom = nom;
        this.tauxTVA = tauxTVA;
        this.typeComposant = typeComposant;
        this.idProjet=idProjet;
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

    public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

    @Override
    public String toString() {
        return "Composant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", tauxTVA=" + tauxTVA +
                ", typeComposant=" + typeComposant +
                ", idProjet=" + idProjet +
                '}';
    }

	
}
