package br.com.racc.client.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.racc.client.domain.Client;
import br.com.racc.exception.BusinessException;

@Stateless
@Named
@Path("/client")
public class ClientWS {

	@EJB
	ClientService clientService;

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/login")
	public String login(@FormParam("email") String email, @FormParam("password") String password) {
		try {
			return clientService.login(email, password);
		} catch (BusinessException e) {

		}

		return null;
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/")
	public Client insertClient(@FormParam("name") String name, @FormParam("email") String email, @FormParam("registrationDate") String registrationDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd");
		Date registrationDateAsDate = null;
		try {
			registrationDateAsDate = simpleDateFormat.parse(registrationDate);
		} catch (ParseException e) {
			//TODO
		}

		return clientService.insert(name, email, registrationDateAsDate);
	}
}
