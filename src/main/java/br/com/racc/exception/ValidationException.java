package br.com.racc.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorCode errorCode;

	public ValidationException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
