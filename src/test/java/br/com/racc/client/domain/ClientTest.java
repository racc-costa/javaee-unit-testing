package br.com.racc.client.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.racc.DateUtil;
import br.com.racc.exception.ErrorCode;
import br.com.racc.exception.RequiredException;
import br.com.racc.exception.ValidationException;

public class ClientTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testClient() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().build();
		client.validate();
		assertNull(client.getId());
		assertThat(ClientDataBuilder.NAME, equalTo(client.getName()));
		assertThat(ClientDataBuilder.EMAIL, equalTo(client.getEmail()));
		assertThat(ClientDataBuilder.REGISTRATION_DATE, equalTo(client.getRegistrationDate()));
		assertThat(ClientStatus.DISABLED, equalTo(client.getStatus()));
	}

	@Test(expected = RequiredException.class)
	public void testNameIsNull() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withName(null).build();
		client.validate();
	}

	@Test(expected = RequiredException.class)
	public void testNameIsEmpty() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withName("").build();
		client.validate();
	}

	@Test
	public void testEmailIsNull() throws ValidationException {
		Client client = new ClientDataBuilder().withEmail(null).build();
		try {
			client.validate();
			fail("E-mail validation fail.");
		} catch (RequiredException e) {
			assertThat(e.getMessage(), equalTo("E-mail is required."));
		}
	}

	@Test
	public void testEmailIsEmpty() throws ValidationException {
		Client client = new ClientDataBuilder().withEmail("").build();
		try {
			client.validate();
			fail("E-mail validation fail.");
		} catch (RequiredException e) {
			assertThat(e.getMessage(), equalTo("E-mail is required."));
		}
	}

	@Test
	public void testRegistrationDateIsNull() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withRegistrationDate(null).build();
		exception.expect(RequiredException.class);
		exception.expectMessage("Registration date is required.");
		client.validate();
	}

	@Test
	public void testRegistrationDateIsBeforeToday() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withRegistrationDate(DateUtil.yesterday()).build();
		exception.expect(ValidationException.class);
		exception.expectMessage("Invalid registration date.");
		exception.expect(hasProperty("errorCode", equalTo(ErrorCode.INVALID_REGISTRATION_DATE)));
		client.validate();
	}

	@Test
	public void testEnable() {
		Client client = new ClientDataBuilder().build();
		assertThat(ClientStatus.DISABLED, equalTo(client.getStatus()));
		client.enable();
		assertThat(ClientStatus.ENABLED, equalTo(client.getStatus()));
	}

	@Test
	public void testDisable() {
		Client client = new ClientDataBuilder().enabled().build();
		assertThat(ClientStatus.ENABLED, equalTo(client.getStatus()));
		client.disable();
		assertThat(ClientStatus.DISABLED, equalTo(client.getStatus()));
	}
}
