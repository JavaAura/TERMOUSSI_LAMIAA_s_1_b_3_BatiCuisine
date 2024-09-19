package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Client;
import metier.EtatProjet;
import metier.Projet;

public class ProjetRepositoryImpl implements ProjetRepository{

	
	 private Connection connection;

	    public ProjetRepositoryImpl(Connection connection) {
	        this.connection = connection;
	    }

	    @Override
	    public void save(Projet projet) {
	        if (projet.getId() == 0) {
	            String sql = "INSERT INTO projet(nom_projet, marge_beneficiaire, cout_total, etat_projet, client_id) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	                statement.setString(1, projet.getNomProjet());
	                statement.setDouble(2, projet.getMargeBeneficiaire());
	                statement.setDouble(3, projet.getCoutTotal());
	                statement.setString(4, projet.getEtatProjet().name()); 
	                statement.setInt(5, projet.getClient().getId());
	                statement.executeUpdate();

	                ResultSet generatedKeys = statement.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    projet.setId(generatedKeys.getInt(1));
	                }
	            } catch (SQLException e) {
	                System.err.println("Erreur lors de l'insertion du projet: " + e.getMessage());
	                throw new RuntimeException(e);
	            }
	        } else {
	            update(projet);
	        }
	    }

	    @Override
	    public void update(Projet projet) {
	        String sql = "UPDATE projet SET nom_projet = ?, marge_beneficiaire = ?, cout_total = ?, etat_projet = ?, client_id = ? WHERE id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, projet.getNomProjet());
	            statement.setDouble(2, projet.getMargeBeneficiaire());
	            statement.setDouble(3, projet.getCoutTotal());
	            statement.setString(4, projet.getEtatProjet().name());
	            statement.setInt(5, projet.getClient().getId());
	            statement.setInt(6, projet.getId());

	            int rowsAffected = statement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Projet avec ID " + projet.getId() + " est modifié avec succés.");
	            } else {
	                System.out.println("Aucun projet trouvé avec ID " + projet.getId() + ".");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la mise à jour du projet: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	    }

	    @Override
	    public void delete(int projetId) {
	        String sql = "DELETE FROM projet WHERE id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, projetId);
	            int rowsAffected = statement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Projet avec ID " + projetId + " est supprimé avec succés.");
	            } else {
	                System.out.println("Aucun projet trouvé avec ID " + projetId + ".");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la suppression du projet: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	    }

	    @Override
	    public Projet findById(int id) {
	        String sql = "SELECT * FROM projet WHERE id = ?";
	        Projet projet = null;
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, id);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                projet = new Projet();
	                projet.setId(resultSet.getInt("id"));
	                projet.setNomProjet(resultSet.getString("nom_projet"));
	                projet.setMargeBeneficiaire(resultSet.getDouble("marge_beneficiaire"));
	                projet.setCoutTotal(resultSet.getDouble("cout_total"));
	               //TODO: enum upper case check
	                projet.setEtatProjet(EtatProjet.valueOf(resultSet.getString("etat_projet"))); 
	                int clientId = resultSet.getInt("client_id");
	                Client client = new Client();
	                client.setId(clientId); 
	                projet.setClient(client);
	            } else {
	                System.out.println("Aucun projet trouvé avec ID " + id + ".");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la recherche du projet: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	        return projet; 
	    }
}
