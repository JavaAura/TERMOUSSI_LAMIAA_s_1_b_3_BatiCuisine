package repository;

import java.sql.Connection;


import database.DbConnection;


public class ComposantRepositoryImpl implements ComposantRepository {
	
    private Connection connection;

    public ComposantRepositoryImpl() {
        this.connection = DbConnection.getInstance().getConnection();
    }

    
   
}
