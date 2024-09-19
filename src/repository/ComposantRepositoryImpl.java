package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbConnection;
import metier.Composant;
import metier.TypeComposant;

public class ComposantRepositoryImpl implements ComposantRepository {
	
    private Connection connection;

    public ComposantRepositoryImpl() {
        this.connection = DbConnection.getInstance().getConnection();
    }

    
    @Override
    public void save(Composant composant) {
        String sql = "INSERT INTO Composant (nom, taux_TVA, type_composant, projet_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, composant.getNom());
            pstmt.setDouble(2, composant.getTauxTVA());
            pstmt.setString(3, composant.getTypeComposant().toString()); 
            pstmt.setInt(4, composant.getIdProjet());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                composant.setId(generatedKeys.getInt(1));
                System.out.println("Composant ajouté avec succès : " + composant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du composant : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Composant findById(int id) {
    	 Composant composant = null;
    	    String sql = "SELECT * FROM Composant WHERE id = ?";
    	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    	        pstmt.setInt(1, id);
    	        ResultSet rs = pstmt.executeQuery();
    	        if (rs.next()) {
    	            composant = new Composant(
    	                rs.getString("nom"),
    	                rs.getDouble("taux_TVA"),
    	                TypeComposant.valueOf(rs.getString("type_composant")),
    	                rs.getInt("projet_id")
    	            );
    	            composant.setId(rs.getInt("id"));
    	            System.out.println("Composant trouvé : " + composant);
    	        } else {
    	            System.out.println("Aucun composant trouvé avec l'id : " + id);
    	        }
    	    } catch (SQLException e) {
    	        System.err.println("Erreur lors de la récupération du composant avec l'id : " + id);
    	        e.printStackTrace();
    	    }
    	    return composant;
    }

    @Override
    public void update(Composant composant) {
    	   String sql = "UPDATE Composant SET nom = ?, taux_TVA = ?, type_composant = ?, projet_id = ? WHERE id = ?";
    	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    	        pstmt.setString(1, composant.getNom());
    	        pstmt.setDouble(2, composant.getTauxTVA());
    	        pstmt.setString(3, composant.getTypeComposant().toString());
    	        pstmt.setInt(4, composant.getIdProjet());
    	        pstmt.setInt(5, composant.getId());

    	        int rowsAffected = pstmt.executeUpdate();
    	        if (rowsAffected > 0) {
    	            System.out.println("Composant mis à jour avec succès : " + composant);
    	        } else {
    	            System.out.println("Aucun composant trouvé avec l'id : " + composant.getId());
    	        }
    	    } catch (SQLException e) {
    	        System.err.println("Erreur lors de la mise à jour du composant : " + composant);
    	        e.printStackTrace();
    	    }
    }

    @Override
    public void delete(int id) {
    	  String sql = "DELETE FROM Composant WHERE id = ?";
    	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    	        pstmt.setInt(1, id);
    	        int rowsAffected = pstmt.executeUpdate();
    	        if (rowsAffected > 0) {
    	            System.out.println("Composant supprimé avec succès, id : " + id);
    	        } else {
    	            System.out.println("Aucun composant trouvé avec l'id : " + id);
    	        }
    	    } catch (SQLException e) {
    	        System.err.println("Erreur lors de la suppression du composant avec l'id : " + id);
    	        e.printStackTrace();
    	    }
    }
}
