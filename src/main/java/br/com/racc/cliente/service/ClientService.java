package br.com.racc.cliente.service;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.racc.client.dao.ClientDAO;
import br.com.racc.client.dao.NotFoundException;
import br.com.racc.client.domain.Client;

@Stateless
@Named
@Path("/client")
public class ClientService {

	@Inject
	ClientDAO clienteDAO;
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/login")
	public Long login(@FormParam("email") String email, @FormParam("password") String password) {
		try {
			Client client = clienteDAO.findByEmail(email);
			if (client.verifyPassword(password)) {
				return client.getId();
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
