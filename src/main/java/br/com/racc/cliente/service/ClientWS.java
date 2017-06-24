package br.com.racc.cliente.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.racc.client.domain.Client;

@Stateless
@Named
@Path("/client")
public class ClientWS {

	@EJB
	ClientService service;

	// @POST
	// @Produces({ MediaType.APPLICATION_JSON })
	// @Path("/login")
	// public Long login(@FormParam("email") String email,
	// @FormParam("password") String password) {
	// try {
	// Client client = clienteDAO.findByEmail(email);
	// if (client.verifyPassword(password)) {
	// return client.getId();
	// }
	// } catch (NotFoundException e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/list")
	public List<Client> getAll() {
		return service.getAll();
	}
}
