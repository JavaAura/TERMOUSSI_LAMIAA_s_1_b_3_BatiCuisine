package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import metier.Devis;
import metier.Projet;

public class DevisRepositoryImpl  implements DevisRepository  {
	
	private Connection connection;

    public DevisRepositoryImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void save(Devis devis) {
    	  String sql = "INSERT INTO devis(montant_estime, date_emission, date_validite, accepte, projet_id) VALUES (?, ?, ?, ?, ?)";
          try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
              statement.setDouble(1, devis.getMontantEstime());
              statement.setDate(2, java.sql.Date.valueOf(devis.getDateEmission()));
              statement.setDate(3, java.sql.Date.valueOf(devis.getDateValidite()));
              statement.setBoolean(4, devis.isAccepte());
              statement.setInt(5, devis.getProjet().getId());
              statement.executeUpdate();

              ResultSet generatedKeys = statement.getGeneratedKeys();
              if (generatedKeys.next()) {
                  devis.setId(generatedKeys.getInt(1));
              }
          } catch (SQLException e) {
              System.err.println("Erreur lors de l'insertion du devis: " + e.getMessage());
              throw new RuntimeException(e);
          }
    }
    
    @Override
    public Devis findById(int id) {
    	   String sql = "SELECT * FROM devis WHERE id = ?";
    	    Devis devis = null;
    	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
    	        statement.setInt(1, id);
    	        ResultSet resultSet = statement.executeQuery();

    	        if (resultSet.next()) {
    	            devis = new Devis();
    	            devis.setId(resultSet.getInt("id"));
    	            devis.setMontantEstime(resultSet.getDouble("montant_estime"));
    	            devis.setDateEmission(resultSet.getDate("date_emission").toLocalDate());
    	            devis.setDateValidite(resultSet.getDate("date_validite").toLocalDate());
    	            devis.setAccepte(resultSet.getBoolean("accepte"));

    	            int projetId = resultSet.getInt("projet_id");
    	            ProjetRepository projetRepository= new ProjetRepositoryImpl();
    	            Projet projet = projetRepository.findById(projetId);
    	            devis.setProjet(projet);
    	        } else {
    	            System.out.println("Aucun devis trouvé avec ID " + id + ".");
    	        }
    	    } catch (SQLException e) {
    	        System.err.println("Erreur lors de la recherche du devis: " + e.getMessage());
    	        throw new RuntimeException(e);
    	    }
    	    return devis;
    }



}
