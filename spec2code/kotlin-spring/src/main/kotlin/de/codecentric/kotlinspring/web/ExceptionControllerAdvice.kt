package de.codecentric.kotlinspring.web

import de.codecentric.kotlinspring.api.model.ProblemDTO
import de.codecentric.kotlinspring.persistence.NotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ProblemDTO> {
        val problem: Problem = createProblem(NOT_FOUND, ex)
        return ResponseEntity.status(problem.status).body(problem.toDTO())
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ProblemDTO> {
        val problem: Problem = createProblem(BAD_REQUEST, ex)
        return ResponseEntity.status(problem.status).body(problem.toDTO())
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        ex: HttpRequestMethodNotSupportedException,
    ): ResponseEntity<ProblemDTO> {
        val problem: Problem = createProblem(METHOD_NOT_ALLOWED, ex)
        return ResponseEntity.status(problem.status).body(problem.toDTO())
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<ProblemDTO> {
        val problem: Problem = createProblem(BAD_REQUEST, ex)
        return ResponseEntity.status(problem.status).body(problem.toDTO())
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ProblemDTO> {
        if (ex is ServletRequestBindingException) {
            val problemDetail = ex.body
            val problem: Problem = createProblem(problemDetail.status, ex)
            return ResponseEntity.status(problem.status).body(problem.toDTO())
        } else {
            val problem: Problem = createProblem(INTERNAL_SERVER_ERROR, "something went wrong")
            return ResponseEntity.status(problem.status).body(problem.toDTO())
        }
    }
}
