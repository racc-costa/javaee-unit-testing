package br.com.racc.client.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.enterprise.context.Dependent;

import br.com.racc.exception.InfrastructureException;

@Dependent
public class AuthenticationServer {

	public String login(String email, String password) throws InfrastructureException {
		return cryptWithMD5(new Date().toString() + email);
	}

	public static String encrypt(String cleanPassword) throws InfrastructureException {
		return cryptWithMD5(cleanPassword);
	}

	private static String cryptWithMD5(String cleanPassword) throws InfrastructureException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passBytes = cleanPassword.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new InfrastructureException("MD5 algorithm not found.", e);
		}
	}
}
