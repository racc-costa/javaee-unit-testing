package br.com.racc.client.dao;

import java.util.List;

import br.com.racc.client.domain.Client;
import br.com.racc.exception.NotFoundException;

public interface ClientDAO {

	public Client save(Client client);

	public Client findById(Long clientID);

	public Client findByEmail(String email) throws NotFoundException;

	public List<Client> findAll();

	public void update(Client client);

	public void remove(Long clientId) throws NotFoundException;

}
