package br.com.start.meupet.common.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class EntityConflictException extends ProblemDetailsException {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityConflictException(String detail) {
        super("Conflict", detail, HttpStatus.CONFLICT);
    }

}
