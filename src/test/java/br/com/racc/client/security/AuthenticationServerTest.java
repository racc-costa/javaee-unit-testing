package br.com.racc.client.security;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.racc.exception.InfrastructureException;

public class AuthenticationServerTest {

   @Test
   public void testLogin() throws InfrastructureException {
      AuthenticationServer authenticationServer = new AuthenticationServer();
      String token = authenticationServer.login("email", "pass");
      assertNotNull(token);
   }
}
