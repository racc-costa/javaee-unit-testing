package br.com.racc.client.service;

import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import br.com.racc.client.dao.ClientDAO;
import br.com.racc.client.domain.Client;
import br.com.racc.client.security.AuthenticationServer;
import br.com.racc.exception.BusinessException;
import br.com.racc.exception.InfrastructureException;
import br.com.racc.exception.NotFoundException;

@Stateless
@LocalBean
public class ClientService {
	@Inject
	@Dependent
	private ClientDAO clientDAO;

	@Inject
	@Dependent
	private AuthenticationServer authenticationServer;

	public String login(String email, String password) throws BusinessException, InfrastructureException {
		Client client = null;

		try {
			client = clientDAO.findByEmail(email);
		} catch (NotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
		if (!client.isAccessAllowed()) {
			throw new BusinessException("Access not allowed.");
		}

		String token = authenticationServer.login(email, password);
		if (token == null) {
			throw new BusinessException("Wrong password.");
		}

		client.updateLastAccessDate();

		return token;
	}

	public Client insert(String name, String email, Date registrationDate) throws BusinessException {
      try {
         clientDAO.findByEmail(email);
         throw new BusinessException("E-mail already registered.");
      }
      catch (NotFoundException e) {
         Client newClient = new Client(name, email, registrationDate);
         return clientDAO.save(newClient);
	   }
	}
	
	public void allowAccess(String email, String password) throws BusinessException, InfrastructureException {
	   
	   Client client = null;
	   
	   try {
	      client = clientDAO.findByEmail(email);
      } catch (NotFoundException e) {
         throw new BusinessException(e.getMessage(), e);
      }
	   
	   String newPassword = AuthenticationServer.encrypt(password);
	   client.allowAccess(newPassword);
	}
}
