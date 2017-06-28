package br.com.racc.cliente.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import br.com.racc.client.dao.ClientDAOImpl;
import br.com.racc.client.domain.Client;
import br.com.racc.client.domain.ClientDataBuilder;
import br.com.racc.client.service.ClientService;
import br.com.racc.exception.BusinessException;
import br.com.racc.exception.NotFoundException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = ClientService.class)
public class ClientServicePowerMockTest {

   @Mock
   private ClientDAOImpl clientDAO;
   
   @InjectMocks
   private ClientService clientService;
   
   @Test
   public final void testAllowAccess() throws BusinessException, NotFoundException {
      Client client = Mockito.mock(Client.class);
      when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenReturn(client);
      ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
      
      PowerMockito.mockStatic(Math.class);
      Mockito.when(Math.random()).thenReturn(Double.valueOf(0.655));
      
      clientService.allowAccess(ClientDataBuilder.EMAIL, ClientDataBuilder.PASSWORD);
      verify(client, times(1)).allowAccess((argument.capture()));
      assertThat(ClientDataBuilder.PASSWORD + "0.655", equalTo(argument.getValue()));
   }
}
