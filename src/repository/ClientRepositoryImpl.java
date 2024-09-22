package repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbConnection;
import metier.Client;

public class ClientRepositoryImpl implements ClientRepository{
	
	  private final Connection connection;

	    public ClientRepositoryImpl() {
	        this.connection = DbConnection.getInstance().getConnection(); 
	    }
	    
	    @Override
	    public void save(Client client) {
	    	String sql = "INSERT INTO client (nom, adresse, email, telephone) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            pstmt.setString(1, client.getNom());
	            pstmt.setString(2, client.getAdresse());
	            pstmt.setString(3, client.getEmail());
	            pstmt.setString(4, client.getTelephone());
	            pstmt.executeUpdate();
	            
	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    client.setId(generatedKeys.getInt(1));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void delete(int clientId) {
	    	String sql = "DELETE FROM client WHERE id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, clientId);
	            int rowsAffected = statement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Client avec ID " + clientId + " est supprimé avec succés.");
	            } else {
	                System.out.println("Aucun client trouvé avec ID " + clientId + ".");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la suppression du client: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	    }

	    @Override
	    public Client findById(int id) {
	    	 String sql = "SELECT * FROM client WHERE id = ?";
	    	    Client client = null; 
	    	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    	        statement.setInt(1, id);
	    	        ResultSet resultSet = statement.executeQuery();

	    	        if (resultSet.next()) {
	    	            client = new Client();
	    	            client.setId(resultSet.getInt("id"));
	    	            client.setNom(resultSet.getString("nom"));
	    	            client.setAdresse(resultSet.getString("adresse"));
	    	            client.setEmail(resultSet.getString("email"));
	    	            client.setTelephone(resultSet.getString("telephone"));
	    	        } else {
	    	            System.out.println("Aucun client trouvé avec ID " + id + ".");
	    	        }
	    	    } catch (SQLException e) {
	    	        System.err.println("Erreur lors de la recherche du client: " + e.getMessage());
	    	        throw new RuntimeException(e); 
	    	    }
	    	    return client;
	    }
	    
	    @Override
	    public void update(Client client) {
	        String sql = "UPDATE client SET nom = ?, adresse = ?, email = ?, telephone = ? WHERE id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, client.getNom());
	            statement.setString(2, client.getAdresse());
	            statement.setString(3, client.getEmail());
	            statement.setString(4, client.getTelephone());
	            statement.setInt(5, client.getId());

	            int rowsAffected = statement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Client avec ID " + client.getId() + " est modifié avec succés.");
	            } else {
	                System.out.println("No client found with ID " + client.getId() + ".");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la mise à jour du client: " + e.getMessage());
	            throw new RuntimeException(e); 
	        }
	    }

	    @Override
	    public Client findByEmail(String email) {
	        String sql = "SELECT * FROM client WHERE email = ?";
	        Client client = null;

	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, email);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                client = new Client();
	                client.setId(resultSet.getInt("id"));
	                client.setNom(resultSet.getString("nom"));
	                client.setAdresse(resultSet.getString("adresse"));
	                client.setEmail(resultSet.getString("email"));
	                client.setTelephone(resultSet.getString("telephone"));
	            } else {
	                System.out.println("Aucun client trouvé avec l'email " + email + ".");
	            }
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la recherche du client par email: " + e.getMessage());
	            throw new RuntimeException(e);
	        }

	        return client;
	    }

	   

}
