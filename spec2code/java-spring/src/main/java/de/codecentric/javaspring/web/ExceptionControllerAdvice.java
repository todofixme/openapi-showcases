package de.codecentric.javaspring.web;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.codecentric.javaspring.api.model.Problem;
import de.codecentric.javaspring.persistence.NotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(NotFoundException ex) {
        final Error error = new Error(NOT_FOUND, ex);
        return ResponseEntity.status(error.status()).body(
                new Problem().type(error.type()).title(error.title()).status(error.status()).detail(error.detail()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Problem> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        final Error error = new Error(BAD_REQUEST, ex);
        return ResponseEntity.status(error.status()).body(
                new Problem().type(error.type()).title(error.title()).status(error.status()).detail(error.detail()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Problem> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        final Error error = new Error(METHOD_NOT_ALLOWED, ex);
        return ResponseEntity.status(error.status()).body(
                new Problem().type(error.type()).title(error.title()).status(error.status()).detail(error.detail()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Problem> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        final Error error = new Error(BAD_REQUEST, ex);
        return ResponseEntity.status(error.status()).body(
                new Problem().type(error.type()).title(error.title()).status(error.status()).detail(error.detail()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleGenericException(Exception ex) {
        if (ex instanceof ServletRequestBindingException srbe) {
            final ProblemDetail problemDetail = srbe.getBody();
            final Error error = new Error(problemDetail.getStatus(), srbe);
            return ResponseEntity.status(error.status()).body(new Problem().type(error.type()).title(error.title())
                    .status(error.status()).detail(error.detail()));
        } else {
            final Error error = new Error(INTERNAL_SERVER_ERROR, "something went wrong");
            return ResponseEntity.status(error.status()).body(new Problem().type(error.type()).title(error.title())
                    .status(error.status()).detail(error.detail()));
        }
    }
}
