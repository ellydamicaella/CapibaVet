package br.com.start.meupet.common.exceptions;

import java.io.Serial;

import org.springframework.http.HttpStatus;

public class ProblemDetailsException extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    
    public String title;
    public String detail;
    public HttpStatus httpStatus;

    public ProblemDetailsException(String title, String detail, HttpStatus httpStatus) {
        super(title);
        this.title = title;
        this.detail = detail;
        this.httpStatus = httpStatus;
    }

}
