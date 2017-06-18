package br.com.racc.exception;

public class RequiredException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequiredException(String message) {
		super(message);
	}
}
