package br.com.racc.cliente.service;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import br.com.racc.client.dao.ClientDAOImpl;
import br.com.racc.client.service.ClientService;

//@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

	//@InjectMocks 
	ClientService service = new ClientService();
	//@Mock
	ClientDAOImpl clienteDAO;
	
	@Test
	@Ignore
	public final void testLogin() {
		MockitoAnnotations.initMocks(this);
		//service.login("", "");
	}

}
