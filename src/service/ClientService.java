package service;

import java.util.Optional;

import metier.Client;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;


public class ClientService {

	 private final ClientRepository clientRepository;

	    public ClientService() {
	        this.clientRepository = new ClientRepositoryImpl();

	    }

	    public Optional<Client> createClient(Client client) {
	        clientRepository.save(client); 
	        return Optional.of(client);
	    }

	    public Client getClientById(int id) {
	        return clientRepository.findById(id);
	    }

	    public void updateClient(Client client) {
	        clientRepository.update(client);
	    }

	    public void deleteClient(int id) {
	        clientRepository.delete(id);
	    }
	    
	    public Optional<Client> getClientByEmail(String email) {
	        Client client = clientRepository.findByEmail(email);
	        return Optional.ofNullable(client);
	    }


}
