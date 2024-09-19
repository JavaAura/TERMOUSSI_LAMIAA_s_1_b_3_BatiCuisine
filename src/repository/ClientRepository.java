package repository;



import metier.Client;

public interface ClientRepository {
	
		void save(Client client);
	    void delete(int id);
	    Client findById(int id);
	    void update(Client client); 
}
