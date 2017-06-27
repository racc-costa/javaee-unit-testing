package br.com.racc.client.dao;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import br.com.racc.client.domain.Client;
import br.com.racc.exception.NotFoundException;

@Dependent
public class ClientDAOImpl implements ClientDAO {

	@PersistenceContext(unitName = "H2PersistenceUnit")
	EntityManager entityManager;

	public Client save(Client client) {
		entityManager.persist(client);
		entityManager.flush();
		entityManager.refresh(client);
		return client;
	}

	public Client findById(Long clientID) {
		return entityManager.find(Client.class, clientID);
	}

	public Client findByEmail(String email) throws NotFoundException {
		Query query = entityManager.createQuery("SELECT c FROM " + Client.class.getCanonicalName() + " c WHERE c.email = :email");
		query.setParameter("email", email);
		Client client = (Client) query.getSingleResult();
		
		if (client == null) {
         throw new NotFoundException("Client not found.");
      }
		
		return client; 
	}

	@SuppressWarnings("unchecked")
	public List<Client> findAll() {
		Query query = entityManager.createQuery("SELECT c FROM " + Client.class.getCanonicalName() + " c");
		return query.getResultList();
	}

	public void update(Client client) {
		entityManager.merge(client);
	}

	public void remove(Long clientID) throws NotFoundException {
		Client client = findById(clientID);
		if (client == null) {
			throw new NotFoundException("Client not found.");
		}
		entityManager.remove(client);
	}
}
