package br.com.start.meupet.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyUsedException() {
		super();
	}

	public EmailAlreadyUsedException(String message) {
		super(message);
	}

}
