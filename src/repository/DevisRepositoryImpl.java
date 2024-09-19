package repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbConnection;
import metier.Devis;


public class DevisRepositoryImpl  implements DevisRepository  {
	
	private Connection connection;

    public DevisRepositoryImpl() {
        this.connection = DbConnection.getInstance().getConnection();

    }
    
    @Override
    public void save(Devis devis) {
    	  String sql = "INSERT INTO devis (montant_estime, date_emission, date_validite, accepte, projet_id) VALUES (?, ?, ?, ?, ?)";
          try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
              pstmt.setDouble(1, devis.getMontantEstime());
              pstmt.setDate(2, java.sql.Date.valueOf(devis.getDateEmission()));
              pstmt.setDate(3, java.sql.Date.valueOf(devis.getDateValidite()));
              pstmt.setBoolean(4, devis.isAccepte());
              pstmt.setInt(5, devis.getIdProjet());
              pstmt.executeUpdate();

              ResultSet generatedKeys = pstmt.getGeneratedKeys();
              if (generatedKeys.next()) {
                  devis.setId(generatedKeys.getInt(1));
                  System.out.println("Devis ajouté avec succès : " + devis);
              }
          } catch (SQLException e) {
              System.err.println("Erreur lors de l'ajout du devis : " + e.getMessage());
              e.printStackTrace();
          }
    }
    
    @Override
    public Devis findById(int id) {
    	 Devis devis = null;
         String sql = "SELECT * FROM devis WHERE id = ?";
         try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
             pstmt.setInt(1, id);
             ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                 devis = new Devis();
                 devis.setId(rs.getInt("id"));
                 devis.setMontantEstime(rs.getDouble("montant_estime"));
                 devis.setDateEmission(rs.getDate("date_emission").toLocalDate());
                 devis.setDateValidite(rs.getDate("date_validite").toLocalDate());
                 devis.setAccepte(rs.getBoolean("accepte"));
                 devis.setIdProjet(rs.getInt("projet_id"));
                 System.out.println("Devis trouvé : " + devis);
             } else {
                 System.out.println("Aucun devis trouvé avec l'id : " + id);
             }
         } catch (SQLException e) {
             System.err.println("Erreur lors de la récupération du devis avec l'id : " + id);
             e.printStackTrace();
         }
         return devis;
    }

    @Override
    public void update(Devis devis) {
        String sql = "UPDATE devis SET montant_estime = ?, date_emission = ?, date_validite = ?, accepte = ?, projet_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, devis.getMontantEstime());
            pstmt.setDate(2, java.sql.Date.valueOf(devis.getDateEmission()));
            pstmt.setDate(3, java.sql.Date.valueOf(devis.getDateValidite()));
            pstmt.setBoolean(4, devis.isAccepte());
            pstmt.setInt(5, devis.getIdProjet());
            pstmt.setInt(6, devis.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Devis avec l'id " + devis.getId() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucun devis trouvé avec l'id : " + devis.getId() + ". Mise à jour échouée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du devis avec l'id : " + devis.getId());
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM devis WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Devis avec l'id " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun devis trouvé avec l'id : " + id + ". Suppression échouée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du devis avec l'id : " + id);
            e.printStackTrace();
        }
    }


}
