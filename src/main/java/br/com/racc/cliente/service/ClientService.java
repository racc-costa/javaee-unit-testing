package br.com.racc.cliente.service;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Named
@Path("/client")
public class ClientService {

	@GET
    @Produces({MediaType.APPLICATION_JSON})
	@Path("/count")
	public int getClientsCount
	() {
		return 12500;
	}
}
