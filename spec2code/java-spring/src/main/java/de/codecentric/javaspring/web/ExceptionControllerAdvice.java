package de.codecentric.javaspring.web;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.codecentric.javaspring.api.model.ProblemDTO;
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
    public ResponseEntity<ProblemDTO> handleNotFoundException(NotFoundException ex) {
        final Problem problem = new Problem(NOT_FOUND, ex);
        return ResponseEntity.status(problem.status()).body(new ProblemDTO().type(problem.type()).title(problem.title())
                .status(problem.status()).detail(problem.detail()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        final Problem problem = new Problem(BAD_REQUEST, ex);
        return ResponseEntity.status(problem.status()).body(new ProblemDTO().type(problem.type()).title(problem.title())
                .status(problem.status()).detail(problem.detail()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDTO> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        final Problem problem = new Problem(METHOD_NOT_ALLOWED, ex);
        return ResponseEntity.status(problem.status()).body(new ProblemDTO().type(problem.type()).title(problem.title())
                .status(problem.status()).detail(problem.detail()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDTO> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        final Problem problem = new Problem(BAD_REQUEST, ex);
        return ResponseEntity.status(problem.status()).body(new ProblemDTO().type(problem.type()).title(problem.title())
                .status(problem.status()).detail(problem.detail()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDTO> handleGenericException(Exception ex) {
        if (ex instanceof ServletRequestBindingException srbe) {
            final ProblemDetail problemDetail = srbe.getBody();
            final Problem problem = new Problem(problemDetail.getStatus(), srbe);
            return ResponseEntity.status(problem.status()).body(new ProblemDTO().type(problem.type())
                    .title(problem.title()).status(problem.status()).detail(problem.detail()));
        } else {
            final Problem problem = new Problem(INTERNAL_SERVER_ERROR, "something went wrong");
            return ResponseEntity.status(problem.status()).body(new ProblemDTO().type(problem.type())
                    .title(problem.title()).status(problem.status()).detail(problem.detail()));
        }
    }
}
