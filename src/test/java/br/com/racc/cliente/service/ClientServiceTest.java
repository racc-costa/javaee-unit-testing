package br.com.racc.cliente.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Date;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import br.com.racc.client.dao.ClientDAOImpl;
import br.com.racc.client.domain.Client;
import br.com.racc.client.domain.ClientDataBuilder;
import br.com.racc.client.security.AuthenticationServer;
import br.com.racc.client.service.ClientService;
import br.com.racc.exception.BusinessException;
import br.com.racc.exception.NotFoundException;

@RunWith(PowerMockRunner.class)
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
      when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenThrow(new NotFoundException("Client not found."));
      
      exception.expect(BusinessException.class);
      exception.expectMessage("Client not found.");
      
      clientService.login(ClientDataBuilder.EMAIL, "pass");
	}

	@Test
	public final void testLoginWithWrongPassword() throws BusinessException, NotFoundException {
		Client client = Mockito.mock(Client.class);
		when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenReturn(client);
		when(authenticationServer.login(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

		exception.expect(BusinessException.class);
		exception.expectMessage("Wrong password.");

		clientService.login(ClientDataBuilder.EMAIL, "pass");
	}

	@Test
	public final void testLogin() throws BusinessException, NotFoundException {
		Client client = Mockito.mock(Client.class);
		when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenReturn(client);
		String token = "--zxsx0120FHQ12xzsDSSwe510021k";
		when(authenticationServer.login(Mockito.anyString(), Mockito.anyString())).thenReturn(token);

		assertThat(clientService.login(ClientDataBuilder.EMAIL, "pass"), equalTo((token)));
		verify(client, times(1)).updateLastAccessDate();
	}
	
	@Test
	public final void testInsertWithEmailAlreadyRegistered() throws BusinessException, NotFoundException {
      exception.expect(BusinessException.class);
      exception.expectMessage("E-mail already registered.");
      
      clientService.insert("", "", new Date());
	}
	
	@Test
	public final void testInsert() throws BusinessException, NotFoundException {
      when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenThrow(new NotFoundException("Client not found."));
      ArgumentCaptor<Client> argument = ArgumentCaptor.forClass(Client.class);
	   clientService.insert(ClientDataBuilder.NAME, ClientDataBuilder.EMAIL, ClientDataBuilder.REGISTRATION_DATE);
	   verify(clientDAO, times(1)).save(argument.capture());
	   assertThat(argument.getValue().getName(), equalTo(ClientDataBuilder.NAME));
      assertThat(argument.getValue().getEmail(), equalTo(ClientDataBuilder.EMAIL));
      assertThat(argument.getValue().getRegistrationDate(), equalTo(ClientDataBuilder.REGISTRATION_DATE));
	}
	
	@Test
   public final void testAllowAccessWrongEmail() throws BusinessException, NotFoundException {
      when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenThrow(new NotFoundException("Client not found."));
      exception.expect(BusinessException.class);
      exception.expectMessage("Client not found.");
      clientService.allowAccess(ClientDataBuilder.EMAIL, ClientDataBuilder.PASSWORD);
   }
	
	@Test
	public final void testAllowAccess() throws BusinessException, NotFoundException {
      Client client = Mockito.mock(Client.class);
      when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenReturn(client);
      ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
      clientService.allowAccess(ClientDataBuilder.EMAIL, ClientDataBuilder.PASSWORD);
      verify(client, times(1)).allowAccess((argument.capture()));
      assertNotNull(argument.getValue());
	}
}
