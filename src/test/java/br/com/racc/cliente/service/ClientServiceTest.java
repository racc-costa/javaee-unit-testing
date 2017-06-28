package br.com.racc.cliente.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;
import java.util.Date;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import br.com.racc.client.dao.ClientDAOImpl;
import br.com.racc.client.domain.Client;
import br.com.racc.client.domain.ClientDataBuilder;
import br.com.racc.client.security.AuthenticationServer;
import br.com.racc.client.service.ClientService;
import br.com.racc.exception.BusinessException;
import br.com.racc.exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

   @Mock
   private ClientDAOImpl clientDAO;
   
   @Mock
   private AuthenticationServer authenticationServer;
   
   @InjectMocks
   private ClientService clientService;

   @Rule
   public ExpectedException exception = ExpectedException.none();
	
	@Test
	public final void testLoginWithClientNotFound() throws BusinessException, NotFoundException {
      when(clientDAO.findByEmail(Mockito.anyString())).thenThrow(new NotFoundException("Client not found."));
      
      exception.expect(BusinessException.class);
      exception.expectMessage("Client not found.");
      setInternalState(clientService, "clientDAO", clientDAO);
      
      clientService.login("user", "pass");
	}

	@Test
	public final void testLoginWithWrongPassword() throws BusinessException, NotFoundException {
		Client client = Mockito.mock(Client.class);
		when(clientDAO.findByEmail(Mockito.anyString())).thenReturn(client);
		when(authenticationServer.login(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

		exception.expect(BusinessException.class);
		exception.expectMessage("Wrong password.");

		clientService.login("user", "pass");
	}

	@Test
	public final void testLogin() throws BusinessException, NotFoundException {
		Client client = Mockito.mock(Client.class);
		when(clientDAO.findByEmail(Mockito.anyString())).thenReturn(client);
		String token = "--zxsx0120FHQ12xzsDSSwe510021k";
		when(authenticationServer.login(Mockito.anyString(), Mockito.anyString())).thenReturn(token);

		assertThat(clientService.login("user", "pass"), equalTo((token)));
		verify(client).updateLastAccessDate();
	}
	
	@Test
	public final void testInsertWithEmailAlreadyRegistered() throws BusinessException, NotFoundException {
      exception.expect(BusinessException.class);
      exception.expectMessage("E-mail already registered.");
      
      clientService.insert("", "", new Date());
	}
	
	@Test
	public final void testInsert() throws BusinessException, NotFoundException {
      when(clientDAO.findByEmail(Mockito.anyString())).thenThrow(new NotFoundException("Client not found."));
      ArgumentCaptor<Client> argument = ArgumentCaptor.forClass(Client.class);
	   clientService.insert(ClientDataBuilder.NAME, ClientDataBuilder.EMAIL, ClientDataBuilder.REGISTRATION_DATE);
	   verify(clientDAO).save(argument.capture());
	   assertThat(argument.getValue().getName(), equalTo(ClientDataBuilder.NAME));
      assertThat(argument.getValue().getEmail(), equalTo(ClientDataBuilder.EMAIL));
      assertThat(argument.getValue().getRegistrationDate(), equalTo(ClientDataBuilder.REGISTRATION_DATE));
	}
}
