package service;

import metier.Client;
import repository.ClientRepository;

public class ClientService {

	 private final ClientRepository clientRepository;

	    public ClientService(ClientRepository clientRepository) {
	        this.clientRepository = clientRepository;
	    }

	    public void createClient(Client client) {
	        clientRepository.save(client);
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

}
