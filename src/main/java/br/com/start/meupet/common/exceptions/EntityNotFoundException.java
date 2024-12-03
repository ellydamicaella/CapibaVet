package br.com.start.meupet.common.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class EntityNotFoundException extends ProblemDetailsException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String detail) {
        super("Entity not found", detail, HttpStatus.NOT_FOUND);
    }

}
