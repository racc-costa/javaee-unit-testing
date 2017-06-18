package br.com.racc.client.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import br.com.racc.client.domain.Client;
import br.com.racc.client.domain.ClientDataBuilder;

public class ClientDAOImplTest {

	private static ClientDAO dao;
	private static EntityManager entityManager;

	@BeforeClass
	public static void setup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("H2PersistenceUnit");
		entityManager = entityManagerFactory.createEntityManager();
		dao = new ClientDAOImpl();
		Whitebox.setInternalState(dao, "entityManager", entityManager);
	}

	@Test
	public final void testSave() {
		entityManager.getTransaction().begin();
		Client clientSaved = dao.save(new ClientDataBuilder().build());
		entityManager.getTransaction().commit();
		
		assertNotNull(clientSaved.getId());
		assertThat(ClientDataBuilder.NAME, equalTo(clientSaved.getName()));
		assertThat(ClientDataBuilder.EMAIL, equalTo(clientSaved.getEmail()));
		assertThat(ClientDataBuilder.REGISTRATION_DATE, equalTo(clientSaved.getRegistrationDate()));
		assertFalse(clientSaved.isAccessAllowed());
	}

	@Test
	public final void testFind() {
		entityManager.getTransaction().begin();
		Client clientSaved = dao.save(new ClientDataBuilder().build());
		Client clientFinded = dao.find(clientSaved.getId());
		entityManager.getTransaction().commit();

		assertThat(clientSaved.getId(), equalTo(clientFinded.getId()));
		assertThat(clientSaved.getName(), equalTo(clientFinded.getName()));
		assertThat(clientSaved.getEmail(), equalTo(clientFinded.getEmail()));
		assertThat(clientSaved.getRegistrationDate(), equalTo(clientFinded.getRegistrationDate()));
		assertThat(clientSaved.isAccessAllowed(), equalTo(clientFinded.isAccessAllowed()));
	}

	@Test
	public final void testFindAll() {
	}

	@Test
	public final void testUpdate() {
	}

	@Test
	public final void testRemove() {
	}

	@Test
	public final void testFindByEmail() {
	}
}
