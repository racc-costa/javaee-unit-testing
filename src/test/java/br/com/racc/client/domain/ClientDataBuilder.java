package br.com.racc.client.domain;

import java.util.Date;

import br.com.racc.DateUtil;

public class ClientDataBuilder {
	public static String NAME = "Josh Silva";
	public static String EMAIL = "josh.silva@zentech.com";
	public static Date REGISTRATION_DATE = DateUtil.today();

	private String name = NAME;
	private String email = EMAIL;
	private Date registrationDate = REGISTRATION_DATE;
	private ClientStatus status;

	public Client build() {
		Client client = new Client(name, email, registrationDate);
		if (status != null && status == ClientStatus.DISABLED) {
			client.disable();
		}
		return client;
	}

	public ClientDataBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ClientDataBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public ClientDataBuilder withRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
		return this;
	}

	public ClientDataBuilder disabled() {
		this.status = ClientStatus.DISABLED;
		return this;
	}
}
