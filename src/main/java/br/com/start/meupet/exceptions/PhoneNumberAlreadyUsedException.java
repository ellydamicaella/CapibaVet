package br.com.start.meupet.exceptions;

import java.io.Serial;

public class PhoneNumberAlreadyUsedException extends RuntimeException {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public PhoneNumberAlreadyUsedException() {
        super();
    }

    public PhoneNumberAlreadyUsedException(String message) {
        super(message);
    }
}
