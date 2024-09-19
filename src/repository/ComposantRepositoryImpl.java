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

    
   
}
