package br.com.start.meupet.common.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ForbiddenActionException extends ProblemDetailsException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public ForbiddenActionException(String detail) {
        super("Forbidden", detail, HttpStatus.FORBIDDEN);
    }
}