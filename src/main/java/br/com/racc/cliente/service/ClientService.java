package br.com.racc.cliente.service;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.racc.client.dao.ClientDAO;
import br.com.racc.client.domain.Client;

@Stateless
@LocalBean
public class ClientService {
	@Inject	@Dependent
	ClientDAO clienteDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Client> getAll() {
		Client client = new Client("John", new Date().toString(), new Date());
		clienteDAO.save(client);
		return clienteDAO.findAll();
	}
}
