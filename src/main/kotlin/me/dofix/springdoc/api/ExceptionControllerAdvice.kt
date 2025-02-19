package me.dofix.springdoc.api

import me.dofix.springdoc.persistence.NotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ProblemDTO> {
        val problem: ProblemDTO = createProblem(NOT_FOUND, ex)
        return ResponseEntity.status(problem.status).body(problem)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ProblemDTO> {
        val problem: ProblemDTO = createProblem(BAD_REQUEST, ex)
        return ResponseEntity.status(problem.status).body(problem)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ProblemDTO> {
        if (ex is ServletRequestBindingException) {
            val problemDetail = ex.body
            val problem: ProblemDTO = createProblem(problemDetail.status, ex)
            return ResponseEntity.status(problem.status).body(problem)
        } else {
            val problem: ProblemDTO = createProblem(INTERNAL_SERVER_ERROR, "something went wrong")
            return ResponseEntity.status(problem.status).body(problem)
        }
    }
}
