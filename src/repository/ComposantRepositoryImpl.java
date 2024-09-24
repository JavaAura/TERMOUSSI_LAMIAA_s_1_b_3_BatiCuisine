package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.DbConnection;
import metier.Composant;


public class ComposantRepositoryImpl implements ComposantRepository {
	
    private Connection connection;

    public ComposantRepositoryImpl() {
        this.connection = DbConnection.getInstance().getConnection();
    }

  
   
}
