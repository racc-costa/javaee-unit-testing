package br.com.racc.client.dao;

import java.util.List;

import br.com.racc.client.domain.Client;
import br.com.racc.exception.NotFoundException;

public interface ClientDAO {

	Client save(Client client);

	Client findById(Long clientID);

	Client findByEmail(String email) throws NotFoundException;

	List<Client> getAll();

	void update(Client client);

	void remove(Long clientId) throws NotFoundException;
}
