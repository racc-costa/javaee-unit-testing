package br.com.racc.client.dao;

import java.util.List;

import br.com.racc.client.domain.Client;

public interface ClientDAO {

	public Client save(Client client);

	public Client find(Long clientID);

	public List<Client> findAll();

	public void update(Client client);

	public void remove(Long clientId) throws NotFoundException;

	public Client findByEmail(String email) throws NotFoundException;
}
