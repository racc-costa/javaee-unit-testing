package br.com.racc.client.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.racc.exception.ErrorCode;
import br.com.racc.exception.RequiredException;
import br.com.racc.exception.ValidationException;
import br.com.racc.util.DateUtil;

@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Client() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLI_ID")
	private Long id;

	@Column(name = "CLI_NAME", nullable = false, columnDefinition = "VARCHAR(60)")
	private String name;

	@Column(name = "CLI_EMAIL", nullable = false, unique = true, columnDefinition = "VARCHAR(120)")
	private String email;

	@Column(name = "CLI_PASSWORD", columnDefinition = "VARCHAR(12)")
	private String password;

	@Column(name = "CLI_REGISTRATION_DATE", nullable = false)
	private Date registrationDate;

	@Column(name = "CLI_LAST_ACCESS_DATE", nullable = true)
	private Date lastAccessDate;

	@Column(name = "CLI_ACCESS_ALLOWED", nullable = false, columnDefinition = "NUMBER(1)")
	private ClientAccess accessAllowed;

	public Client(String name, String email, Date registrationDate) {
		super();
		this.name = name;
		this.email = email;
		this.registrationDate = registrationDate;
		this.accessAllowed = ClientAccess.BLOCKED;
	}

	public void allowAccess(String password) {
		this.password = password;
		this.accessAllowed = ClientAccess.ALLOWED;
	}

	public void blockAccess() {
		this.accessAllowed = ClientAccess.BLOCKED;
		this.password = null;
	}

	public void validate() throws RequiredException, ValidationException {
		if (name == null || name.isEmpty()) {
			throw new RequiredException("Name is required.");
		}

		if (email == null || email.isEmpty()) {
			throw new RequiredException("E-mail is required.");
		}

		if (registrationDate == null) {
			throw new RequiredException("Registration date is required.");
		}

		if (registrationDate.before(DateUtil.today())) {
			throw new ValidationException("Invalid registration date.", ErrorCode.INVALID_REGISTRATION_DATE);
		}
	}

	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void updateLastAccessDate() {
		this.lastAccessDate = new Date();
	}

	public boolean isAccessAllowed() {
		return this.accessAllowed.equals(ClientAccess.ALLOWED);
	}
}
