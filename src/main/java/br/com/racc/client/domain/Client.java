package br.com.racc.client.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.racc.DateUtil;
import br.com.racc.exception.ValidationException;

@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLI_ID")
	private Long id;

	@Column(name = "CLI_NAME")
	private String name;

	@Column(name = "CLI_EMAIL")
	private String email;

	@Column(name = "CLI_REGISTRATION_DATE")
	private Date registrationDate;

	@Column(name = "CLI_REGISTRATION_DATE")
	private ClientStatus status;

	public Client(String name, String email, Date registrationDate) {
		super();
		this.name = name;
		this.email = email;
		this.registrationDate = registrationDate;
		this.status = ClientStatus.DISABLED;
	}

	public void enable() {
		this.status = ClientStatus.ENABLED;
	}

	public void disable() {
		this.status = ClientStatus.DISABLED;
	}

	public void validate() throws ValidationException {
		if (name == null || name.isEmpty()) {
			throw new ValidationException("Name is required.");
		}

		if (email == null || email.isEmpty()) {
			throw new ValidationException("E-mail is required.");
		}

		if (registrationDate == null) {
			throw new ValidationException("Registration date is required.");
		}

		if (registrationDate.before(DateUtil.today())) {
			throw new ValidationException("Invalid registration date.");
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public ClientStatus getStatus() {
		return status;
	}
}
