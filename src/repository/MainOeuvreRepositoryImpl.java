package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DbConnection;
import metier.MainOeuvre;
import metier.Materiau;
import metier.TypeMainOeuvre;

public class MainOeuvreRepositoryImpl  implements MainOeuvreRepository{
    
    private Connection connection;

    public MainOeuvreRepositoryImpl() {
        this.connection = DbConnection.getInstance().getConnection();

    }
    
    @Override
    public void save(MainOeuvre mainOeuvre) {
        String sql = "INSERT INTO mainoeuvre (nom, taux_TVA, type_composant, projet_id, taux_horaire, heures_travail, productivite_ouvrier, type_main_oeuvre) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, mainOeuvre.getNom());
            pstmt.setDouble(2, mainOeuvre.getTauxTVA());
            pstmt.setObject(3, mainOeuvre.getTypeComposant().toString().toLowerCase(), java.sql.Types.OTHER);
            pstmt.setInt(4, mainOeuvre.getIdProjet());
            pstmt.setDouble(5, mainOeuvre.getTauxHoraire());
            pstmt.setDouble(6, mainOeuvre.getHeuresTravail());
            pstmt.setDouble(7, mainOeuvre.getProductiviteOuvrier());
            pstmt.setObject(8, mainOeuvre.getTypeMainOeuvre().toString().toLowerCase(), java.sql.Types.OTHER);   
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                mainOeuvre.setId(generatedKeys.getInt(1));
            }
            System.out.println("MainOeuvre ajoutée avec succès : " + mainOeuvre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de MainOeuvre : " + e.getMessage());
        }
    }
    
    @Override
    public List<MainOeuvre> findAll() {
    	 List<MainOeuvre> mainoeuvres= new ArrayList<>();;
    	String sql ="SELECT * FROM mainoeuvre";
    	try (PreparedStatement pstmt = connection.prepareStatement(sql)){
    	       ResultSet rs = pstmt.executeQuery();
    	       while (rs.next()) {
    	    	   MainOeuvre mainOeuvre = new MainOeuvre(
                           rs.getString("nom"),
                           rs.getDouble("taux_TVA"),
                           rs.getInt("projet_id"),
                           rs.getDouble("taux_horaire"),
                           rs.getDouble("heures_travail"),
                           rs.getDouble("productivite_ouvrier"),
                           TypeMainOeuvre.valueOf(rs.getString("type_main_oeuvre").toUpperCase())
                   );
                   mainOeuvre.setId(rs.getInt("id"));
                   mainoeuvres.add(mainOeuvre);
    	            if (mainoeuvres.isEmpty()) {
    	                System.out.println("Aucun mainoeuvre trouvé");
    	            }
    	        }
    	}catch (SQLException e) {
	        System.err.println("Erreur lors de la récupération des mainoeuvres ");
	        e.printStackTrace();
	    }
	    return mainoeuvres;
    }
    
    @Override
    public MainOeuvre findById(int id) {
        MainOeuvre mainOeuvre = null;
        String sql = "SELECT * FROM Composant WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                mainOeuvre = new MainOeuvre(
                        rs.getString("nom"),
                        rs.getDouble("taux_TVA"),
                        rs.getInt("projet_id"),
                        rs.getDouble("taux_horaire"),
                        rs.getDouble("heures_travail"),
                        rs.getDouble("productivite_ouvrier"),
                        TypeMainOeuvre.valueOf(rs.getString("type_main_oeuvre"))
                );
                mainOeuvre.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de MainOeuvre avec l'id : " + id);
        }
        return mainOeuvre;
    }
    
    @Override
    public void update(MainOeuvre mainOeuvre) {
        String sql = "UPDATE Composant SET nom = ?, taux_TVA = ?, taux_horaire = ?, heures_travail = ?, productivite_ouvrier = ?, type_main_oeuvre = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, mainOeuvre.getNom());
            pstmt.setDouble(2, mainOeuvre.getTauxTVA());
            pstmt.setDouble(3, mainOeuvre.getTauxHoraire());
            pstmt.setDouble(4, mainOeuvre.getHeuresTravail());
            pstmt.setDouble(5, mainOeuvre.getProductiviteOuvrier());
            pstmt.setString(6, mainOeuvre.getTypeMainOeuvre().toString());
            pstmt.setInt(7, mainOeuvre.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("MainOeuvre mise à jour avec succès : " + mainOeuvre);
            } else {
                System.out.println("Aucune MainOeuvre trouvée avec l'id : " + mainOeuvre.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de MainOeuvre : " + e.getMessage());
        }
    }
    
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Composant WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("MainOeuvre supprimée avec succès, id : " + id);
            } else {
                System.out.println("Aucune MainOeuvre trouvée avec l'id : " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de MainOeuvre avec l'id : " + id);
        }
    }

    @Override
	public void updateTVA(MainOeuvre mainOeuvre) {
    	   String sql = "UPDATE mainoeuvre SET taux_tva = ? WHERE id = ?";
    	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
    	        pstmt.setDouble(1, mainOeuvre.getTauxTVA()); 
    	        pstmt.setInt(2, mainOeuvre.getId()); 
    	        pstmt.executeUpdate();
    	       // System.out.println("TVA mise à jour avec succès pour la main d'œuvre avec ID : " + mainOeuvre.getId());
    	    } catch (SQLException e) {
    	        System.err.println("Erreur lors de la mise à jour de la TVA de la main d'œuvre");
    	        e.printStackTrace();
    	    }
	}

}
