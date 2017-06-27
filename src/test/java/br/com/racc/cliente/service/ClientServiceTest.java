package br.com.racc.cliente.service;

import org.junit.Test;

import br.com.racc.client.service.ClientService;
import br.com.racc.exception.BusinessException;

public class ClientServiceTest {

	ClientService clientService = new ClientService();

	@Test
	public final void testLoginWithClientNotFound() throws BusinessException {
		clientService.login("", "");
	}

	@Test
	public final void testLoginWithWrongPassword() throws BusinessException {
		clientService.login("", "");
	}

	@Test
	public final void testLogin() throws BusinessException {
		clientService.login("", "");
	}
}
