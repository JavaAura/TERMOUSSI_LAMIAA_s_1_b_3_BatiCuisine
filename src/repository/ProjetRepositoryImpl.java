package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DbConnection;
import metier.Devis;
import metier.EtatProjet;
import metier.Projet;

public class ProjetRepositoryImpl implements ProjetRepository{

	
	 private Connection connection;

	    public ProjetRepositoryImpl() {
	        this.connection = DbConnection.getInstance().getConnection();
	    }

	    @Override
	    public void save(Projet projet) {
	    	   String sql = "INSERT INTO projet (nom_projet, marge_beneficiaire, cout_total, etat_projet, client_id) VALUES (?, ?, ?, ?, ?)";
	    	    try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    	        pstmt.setString(1, projet.getNomProjet());
	    	        pstmt.setDouble(2, projet.getMargeBeneficiaire());
	    	        pstmt.setDouble(3, projet.getCoutTotal());
	    	        pstmt.setObject(4, projet.getEtatProjet().toString().toLowerCase(), java.sql.Types.OTHER);
	    	        pstmt.setInt(5, projet.getIdClient());
	    	        pstmt.executeUpdate();

	    	        ResultSet generatedKeys = pstmt.getGeneratedKeys();
	    	        if (generatedKeys.next()) {
	    	            projet.setId(generatedKeys.getInt(1));
	    	            System.out.println("Projet ajouté avec succès : " + projet);
	    	        }
	    	    } catch (SQLException e) {
	    	        System.err.println("Erreur lors de l'ajout du projet : " + projet.getNomProjet());
	    	        e.printStackTrace();
	    	    }
	    }

	    @Override
	    public void update(Projet projet) {
	    	  String sql = "UPDATE projet SET nom_projet = ?, marge_beneficiaire = ?, cout_total = ?, etat_projet = ?, client_id = ? WHERE id = ?";
	    	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	    	        pstmt.setString(1, projet.getNomProjet());
	    	        pstmt.setDouble(2, projet.getMargeBeneficiaire());
	    	        pstmt.setDouble(3, projet.getCoutTotal());
	    	        pstmt.setString(4, projet.getEtatProjet().toString());
	    	        pstmt.setInt(5, projet.getIdClient());
	    	        pstmt.setInt(6, projet.getId());
	    	        
	    	        int rowsAffected = pstmt.executeUpdate();
	    	        if (rowsAffected > 0) {
	    	            System.out.println("Projet avec l'id " + projet.getId() + " a été mis à jour avec succès.");
	    	        } else {
	    	            System.out.println("Aucun projet trouvé avec l'id : " + projet.getId() + ". Mise à jour échouée.");
	    	        }
	    	    } catch (SQLException e) {
	    	        System.err.println("Erreur lors de la mise à jour du projet avec l'id : " + projet.getId());
	    	        e.printStackTrace();
	    	    }
	    }

	    @Override
	    public void delete(int projetId) {
	    	String sql = "DELETE FROM projet WHERE id = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setInt(1, projetId);
	            int rowsAffected = pstmt.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Projet avec l'id " + projetId + " a été supprimé avec succès.");
	            } else {
	                System.out.println("Aucun projet trouvé avec l'id : " + projetId + ". Suppression échouée.");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la suppression du projet avec l'id : " + projetId);
	            e.printStackTrace();
	        }
	    }
	
	    @Override 
	    public void updateEtat(Projet projet,EtatProjet nouvelEtat) {
	        String sql = "UPDATE projet SET etat_projet = ? WHERE id = ?";
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setObject(1, projet.getEtatProjet().toString().toLowerCase(), java.sql.Types.OTHER);
	            pstmt.setInt(2, projet.getId()); 
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la mise à jour du projet");
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    public List<Projet> findAll() {
	    	List<Projet> projets = new ArrayList<>();
	    	String sql ="SELECT * FROM projet";
	    	try (PreparedStatement pstmt = connection.prepareStatement(sql)){
	    	       ResultSet rs = pstmt.executeQuery();
	    	       while (rs.next()) {
	    	            Projet projet = new Projet();
	    	            projet.setId(rs.getInt("id"));
	    	            projet.setNomProjet(rs.getString("nom_projet"));
	    	            projet.setMargeBeneficiaire(rs.getDouble("marge_beneficiaire"));
	    	            projet.setCoutTotal(rs.getDouble("cout_total"));
	    	            projet.setEtatProjet(EtatProjet.valueOf(rs.getString("etat_projet").toUpperCase()));
	    	            projet.setIdClient(rs.getInt("client_id"));
	    	            projets.add(projet);
	    	            if (projets.isEmpty()) {
	    	                System.out.println("Aucun projet trouvé");
	    	            }
	    	        }
	    	}catch (SQLException e) {
    	        System.err.println("Erreur lors de la récupération des projets ");
    	        e.printStackTrace();
    	    }
    	    return projets;
	    }
	    
	    @Override
	    public Projet findById(int id) {
	    	 Projet projet = null;
	    	    String sql = "SELECT * FROM projet WHERE id = ?";
	    	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	    	        pstmt.setInt(1, id);
	    	        ResultSet rs = pstmt.executeQuery();
	    	        
	    	        if (rs.next()) {
	    	            projet = new Projet();
	    	            projet.setId(rs.getInt("id"));
	    	            projet.setNomProjet(rs.getString("nom_projet"));
	    	            projet.setMargeBeneficiaire(rs.getDouble("marge_beneficiaire"));
	    	            projet.setCoutTotal(rs.getDouble("cout_total"));
	    	            projet.setEtatProjet(EtatProjet.valueOf(rs.getString("etat_projet").toUpperCase()));
	    	            projet.setIdClient(rs.getInt("client_id"));
	    	           // System.out.println("Projet trouvé : " + projet);
	    	        } else {
	    	            System.out.println("Aucun projet trouvé avec l'id : " + id);
	    	        }
	    	    } catch (SQLException e) {
	    	        System.err.println("Erreur lors de la récupération du projet avec l'id : " + id);
	    	        e.printStackTrace();
	    	    }
	    	    return projet;
	    }
}
