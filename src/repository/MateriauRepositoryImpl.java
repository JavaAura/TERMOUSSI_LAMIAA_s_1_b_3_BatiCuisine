package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DbConnection;
import metier.EtatProjet;
import metier.Materiau;
import metier.Projet;
import service.ProjetService;

public class MateriauRepositoryImpl implements MateriauRepository {

	private Connection connection;
	private ProjetService projetService;
	
    public MateriauRepositoryImpl() {
        this.connection = DbConnection.getInstance().getConnection();
    }
    
    @Override
    public void save(Materiau materiau) {
        String sql = "INSERT INTO materiau (nom, taux_TVA, type_composant, projet_id, cout_unitaire, quantite, cout_transport, coef_qualite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, materiau.getNom());
            pstmt.setDouble(2, materiau.getTauxTVA());
            pstmt.setObject(3, materiau.getTypeComposant().toString().toLowerCase(), java.sql.Types.OTHER);
            pstmt.setInt(4, materiau.getIdProjet());
            pstmt.setDouble(5, materiau.getCoutUnitaire());
            pstmt.setDouble(6, materiau.getQuantite());
            pstmt.setDouble(7, materiau.getCoutTransport());
            pstmt.setDouble(8, materiau.getCoefQualite());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                materiau.setId(generatedKeys.getInt(1));
            }
            System.out.println("Materiau ajouté avec succès : " + materiau);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de Materiau : " + e.getMessage());
        }
    }
    
    @Override
    public List<Materiau> findAll() {
    	 List<Materiau> materiaux= new ArrayList<>();;
    	String sql ="SELECT * FROM materiau";
    	try (PreparedStatement pstmt = connection.prepareStatement(sql)){
    	       ResultSet rs = pstmt.executeQuery();
    	       while (rs.next()) {
    	    	   Materiau materiau = new Materiau(
                           rs.getString("nom"),
                           rs.getDouble("taux_TVA"),
                           rs.getInt("projet_id"),
                           rs.getDouble("cout_unitaire"),
                           rs.getDouble("quantite"),
                           rs.getDouble("cout_transport"),
                           rs.getDouble("coef_qualite")
                   );
                   materiau.setId(rs.getInt("id"));
                   materiaux.add(materiau);
    	            if (materiaux.isEmpty()) {
    	                System.out.println("Aucun materiau trouvé");
    	            }
    	        }
    	}catch (SQLException e) {
	        System.err.println("Erreur lors de la récupération des materiaux ");
	        e.printStackTrace();
	    }
	    return materiaux;
    }
    
    @Override
    public Materiau findById(int id) {
        Materiau materiau = null;
        String sql = "SELECT * FROM materiau WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                materiau = new Materiau(
                        rs.getString("nom"),
                        rs.getDouble("taux_TVA"),
                        rs.getInt("projet_id"),
                        rs.getDouble("cout_unitaire"),
                        rs.getDouble("quantite"),
                        rs.getDouble("cout_transport"),
                        rs.getDouble("coef_qualite")
                );
                materiau.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de Materiau avec l'id : " + id);
        }
        return materiau;
    }
    
    @Override
    public void update(Materiau materiau) {
        String sql = "UPDATE materiau SET nom = ?, taux_TVA = ?, cout_unitaire = ?, quantite = ?, cout_transport = ?, coef_qualite = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, materiau.getNom());
            pstmt.setDouble(2, materiau.getTauxTVA());
            pstmt.setDouble(3, materiau.getCoutUnitaire());
            pstmt.setDouble(4, materiau.getQuantite());
            pstmt.setDouble(5, materiau.getCoutTransport());
            pstmt.setDouble(6, materiau.getCoefQualite());
            pstmt.setInt(7, materiau.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Materiau mis à jour avec succès : " + materiau);
            } else {
                System.out.println("Aucun Materiau trouvé avec l'id : " + materiau.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de Materiau : " + e.getMessage());
        }
    }
    
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM materiau WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Materiau supprimé avec succès, id : " + id);
            } else {
                System.out.println("Aucun Materiau trouvé avec l'id : " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de Materiau avec l'id : " + id);
        }
    }

	public void updateTVA(Materiau materiau) {
		 String sql = "UPDATE materiau SET taux_tva = ? WHERE id = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
		        pstmt.setDouble(1, materiau.getTauxTVA()); 
		        pstmt.setInt(2, materiau.getId()); 
		        pstmt.executeUpdate();
		        // System.out.println("TVA mise à jour avec succès pour le matériau avec ID : " + materiau.getId());
		    } catch (SQLException e) {
		        System.err.println("Erreur lors de la mise à jour de la TVA du matériau");
		        e.printStackTrace();
		    }		
	}
}
