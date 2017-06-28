package br.com.racc.client.security;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;


public class AuthenticationServerTest {

   @Test
   public void testLogin() {
      AuthenticationServer authenticationServer = new AuthenticationServer();
      String token = authenticationServer.login("email", "pass");
      assertNotNull(token);
   }
}
