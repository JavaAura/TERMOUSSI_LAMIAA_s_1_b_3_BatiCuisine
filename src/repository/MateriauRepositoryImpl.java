package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbConnection;
import metier.Materiau;

public class MateriauRepositoryImpl implements MateriauRepository {

	private Connection connection;

    public MateriauRepositoryImpl() {
        this.connection = DbConnection.getInstance().getConnection();
    }
    
    @Override
    public void save(Materiau materiau) {
        String sql = "INSERT INTO materiau (nom, taux_TVA, type_composant, projet_id, cout_unitaire, quantite, cout_transport, coef_qualite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, materiau.getNom());
            pstmt.setDouble(2, materiau.getTauxTVA());
            pstmt.setObject(3, materiau.getTypeComposant(), java.sql.Types.OTHER);
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
}
