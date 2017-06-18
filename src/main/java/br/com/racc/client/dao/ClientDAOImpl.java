package br.com.racc.client.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.racc.client.domain.Client;

@Dependent
public class ClientDAOImpl implements ClientDAO {

	@PersistenceContext
	EntityManager entityManager;

	public Client save(Client client) {
		entityManager.persist(client);
		entityManager.flush(); 
		entityManager.refresh(client);
		return client;
	}
	
	public Client find(Long clientID) {
		return entityManager.find(Client.class, clientID);
	}
	
	@SuppressWarnings("unchecked")
	public List<Client> findAll() {
		Query query = entityManager.createQuery("SELECT * FROM " + Client.class.getCanonicalName());
		return query.getResultList();
	}
	
	public void update(Client client) {
		entityManager.merge(client);
	}

	public void remove(Long clientId) throws NotFoundException {
		Client client = entityManager.find(Client.class, clientId);
		if (client != null) {
			entityManager.remove(client);
		} else {
			throw new NotFoundException("Client not found.");
		}
	}
	
	public Client findByEmail(String email) throws NotFoundException {
		Query query = entityManager.createQuery("SELECT * FROM " + Client.class.getCanonicalName() + " WHERE email = :email");
		query.setParameter("email", email);
		return (Client) query.getSingleResult();
	}
}
