package br.com.racc.cliente.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import br.com.racc.client.dao.ClientDAOImpl;
import br.com.racc.client.service.ClientService;
import br.com.racc.exception.BusinessException;
import br.com.racc.exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

   @Mock
   private ClientDAOImpl clientDAO;
   
   @InjectMocks
   private ClientService clientService;

   @Rule
   public ExpectedException exception = ExpectedException.none();
	
	@Test
	public final void testLoginWithClientNotFound() throws BusinessException, NotFoundException {
      Mockito.when(clientDAO.findByEmail(Mockito.anyString())).thenThrow(new NotFoundException("Client not found."));
      
      exception.expect(BusinessException.class);
      exception.expectMessage("Client not found.");
      Whitebox.setInternalState(clientService, "clientDAO", clientDAO);
      
      clientService.login("user", "pass");
	}

	@Test
	public final void testLoginWithWrongPassword() throws BusinessException {
	}

	@Test
	public final void testLogin() throws BusinessException {
	}
}
