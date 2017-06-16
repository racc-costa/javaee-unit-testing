package br.com.racc.client.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.racc.DateUtil;
import br.com.racc.exception.ValidationException;

public class ClientTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testClient() throws ValidationException {
		Client client = new ClientDataBuilder().build();
		client.validate();
		assertNull(client.getId());
		assertEquals(ClientDataBuilder.NAME, client.getName());
		assertEquals(ClientDataBuilder.EMAIL, client.getEmail());
		assertEquals(ClientDataBuilder.REGISTRATION_DATE, client.getRegistrationDate());
		assertEquals(ClientStatus.DISABLED, client.getStatus());
	}

	@Test(expected = ValidationException.class)
	public void testNameIsNull() throws ValidationException {
		Client client = new ClientDataBuilder().withName(null).build();
		client.validate();
	}

	@Test(expected = ValidationException.class)
	public void testNameIsEmpty() throws ValidationException {
		Client client = new ClientDataBuilder().withName("").build();
		client.validate();
	}

	@Test
	public void testEmailIsNull() {
		Client client = new ClientDataBuilder().withEmail(null).build();
		try {
			client.validate();
			fail("E-mail validation fail.");
		} catch (ValidationException e) {
			assertEquals(e.getMessage(), "E-mail is required.");
		}
	}

	@Test
	public void testEmailIsEmpty() {
		Client client = new ClientDataBuilder().withEmail("").build();
		try {
			client.validate();
			fail("E-mail validation fail.");
		} catch (ValidationException e) {
			assertEquals(e.getMessage(), "E-mail is required.");
		}
	}

	@Test
	public void testRegistrationDateIsNull() throws ValidationException {
		Client client = new ClientDataBuilder().withRegistrationDate(null).build();
		exception.expect(ValidationException.class);
		exception.expectMessage("Registration date is required.");
		client.validate();
	}

	@Test
	public void testRegistrationDateIsBeforeToday() throws ValidationException {
		Client client = new ClientDataBuilder().withRegistrationDate(DateUtil.yesterday()).build();
		exception.expect(ValidationException.class);
		exception.expectMessage("Invalid registration date.");
		client.validate();
	}

	@Test
	public void testEnable() {
		Client client = new ClientDataBuilder().build();
		assertEquals(ClientStatus.DISABLED, client.getStatus());
		client.enable();
		assertEquals(ClientStatus.ENABLED, client.getStatus());
	}

	@Test
	public void testDisable() {
		Client client = new ClientDataBuilder().disabled().build();
		assertEquals(ClientStatus.DISABLED, client.getStatus());
		client.enable();
		assertEquals(ClientStatus.ENABLED, client.getStatus());
	}
}
