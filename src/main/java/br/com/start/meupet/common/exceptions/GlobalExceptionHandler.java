package br.com.start.meupet.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProblemDetailsException.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(ProblemDetailsException ex,
                HttpServletRequest request) {
            Map<String, String> bodyResponse = new HashMap<>();

            bodyResponse.put("type", String.format("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/%s",
                    ex.httpStatus.value()));
            bodyResponse.put("title", ex.title);
            bodyResponse.put("detail", ex.detail);
            bodyResponse.put("instance", request.getRequestURI());

            return ResponseEntity.status(ex.httpStatus).body(bodyResponse);
    }
}
