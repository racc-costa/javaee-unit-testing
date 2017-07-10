package br.com.racc.client.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.racc.client.domain.ClientDataBuilder;
import br.com.racc.exception.InfrastructureException;
import br.com.racc.util.DateUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = AuthenticationServer.class)
public class AuthenticationServerPowerMockTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testLogin() throws Exception {
		AuthenticationServer authenticationServer = new AuthenticationServer();
		PowerMockito.whenNew(Date.class).withAnyArguments().thenReturn(DateUtil.zeroDay());
		String token = authenticationServer.login(ClientDataBuilder.EMAIL, ClientDataBuilder.PASSWORD);
		assertThat("17cd95ec64fc5844c42e34ab6355662", equalTo(token));
	}

	@Test
	public void testLoginNoSuchAlgorithm() throws Exception {
		AuthenticationServer authenticationServer = new AuthenticationServer();
		PowerMockito.mockStatic(MessageDigest.class);
		Mockito.when(MessageDigest.getInstance(Mockito.anyString())).thenThrow(new NoSuchAlgorithmException("NoSuchAlgorithmException"));
		PowerMockito.whenNew(Date.class).withAnyArguments().thenReturn(DateUtil.zeroDay());

		exception.expect(InfrastructureException.class);
		exception.expectMessage("MD5 algorithm not found.");
		exception.expectCause(instanceOf(NoSuchAlgorithmException.class));

		authenticationServer.login(ClientDataBuilder.EMAIL, ClientDataBuilder.PASSWORD);
	}
}
