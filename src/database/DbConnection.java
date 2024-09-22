package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	  private static DbConnection instance;
	    private Connection connection;

	    private static final String URL = "jdbc:postgresql://localhost:5432/bati_cuisine";
	    private static final String USER = "postgres";
	    private static final String PASSWORD = "lamiaa";

	    private DbConnection() {
	        try {
	            Class.forName("org.postgresql.Driver");
	            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println("Connexion à la base de données réussie !");
	        } catch (ClassNotFoundException e) {
	            System.err.println("Erreur : Driver JDBC non trouvé. " + e.getMessage());
	        } catch (SQLException e) {
	            System.err.println("Erreur de connexion à la base de données. " + e.getMessage());
	        }
	    }

	  
	    public static DbConnection getInstance() {
	        if (instance == null) {
	            instance = new DbConnection();
	        }
	        return instance;
	    }

	    public Connection getConnection() {
	        return connection;
	    }


	    public void closeConnection() {
	        if (connection != null) {
	            try {
	                connection.close();
	                System.out.println("Connexion fermée.");
	            } catch (SQLException e) {
	                System.err.println("Erreur lors de la fermeture de la connexion. " + e.getMessage());
	            }
	        }
	    }
}
