package br.com.racc.client.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;

import br.com.racc.client.domain.Client;
import br.com.racc.client.domain.ClientDataBuilder;

public class ClientDAOImplTest {

	private static ClientDAO dao;
	private static EntityManager entityManager;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("H2PersistenceUnit");
		entityManager = entityManagerFactory.createEntityManager();
		dao = new ClientDAOImpl();
		Whitebox.setInternalState(dao, "entityManager", entityManager);
	}
	
	@After
	public void init() {
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("DELETE FROM CLIENT").executeUpdate();
		entityManager.getTransaction().commit();
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
		entityManager.getTransaction().begin();
		Client clientSaved = dao.save(new ClientDataBuilder().build());
		Client clientSaved2 = dao.save(new ClientDataBuilder().withEmail("c2@zzetta.com").allowed().build());
		Client clientSaved3 = dao.save(new ClientDataBuilder().withEmail("c3@zzetta.com").withName("Luis").build());
		List<Client> clients = dao.findAll();
		entityManager.getTransaction().commit();

		assertThat(clients.size(), equalTo(3));
	}

	@Test
	public final void testUpdate() {
		entityManager.getTransaction().begin();
		Client clientSaved = dao.save(new ClientDataBuilder().build());
		clientSaved.setName("Mary Jane");
		clientSaved.setEmail("mj@zetta.com");
		clientSaved.blockAccess();
		dao.update(clientSaved);

		Client clientFinded = dao.find(clientSaved.getId());
		entityManager.getTransaction().commit();

		assertThat(clientSaved.getId(), equalTo(clientFinded.getId()));
		assertThat("Mary Jane", equalTo(clientFinded.getName()));
		assertThat("mj@zetta.com", equalTo(clientFinded.getEmail()));
		assertThat(clientSaved.getRegistrationDate(), equalTo(clientFinded.getRegistrationDate()));
		assertFalse(clientFinded.isAccessAllowed());
	}

	@Test
	public final void testRemove() throws NotFoundException {
		entityManager.getTransaction().begin();
		Client clientSaved = dao.save(new ClientDataBuilder().build());
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		dao.remove(clientSaved.getId());
		entityManager.getTransaction().commit();
	}

	@Test
	public final void testRemoveNotFound() {
		entityManager.getTransaction().begin();
		dao.save(new ClientDataBuilder().build());
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		try {
			dao.remove(888888l);
		} catch (NotFoundException e) {
			entityManager.getTransaction().rollback();
			assertThat(e.getMessage(), equalTo("Client not found."));
		}
	}

	@Test
	public final void testFindByEmail() throws NotFoundException {
		entityManager.getTransaction().begin();
		dao.save(new ClientDataBuilder().build());
		entityManager.getTransaction().commit();
		
		Client clientFinded = dao.findByEmail(ClientDataBuilder.EMAIL);
		assertThat(clientFinded.getName(), equalTo(ClientDataBuilder.NAME));
		assertThat(clientFinded.getEmail(), equalTo(ClientDataBuilder.EMAIL));
	}
}
