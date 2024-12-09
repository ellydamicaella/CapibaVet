package br.com.start.meupet.common.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class StatusInvalidException extends ProblemDetailsException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public StatusInvalidException(String detail) {
        super("Bad request", detail, HttpStatus.BAD_REQUEST);
    }
}