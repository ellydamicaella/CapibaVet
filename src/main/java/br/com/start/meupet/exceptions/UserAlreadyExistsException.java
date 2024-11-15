package br.com.start.meupet.exceptions;

import java.io.Serial;

public class UserAlreadyExistsException extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
