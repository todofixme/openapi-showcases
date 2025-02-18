package me.dofix.springdoc.api

import me.dofix.springdoc.persistence.NotFoundException
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ProblemDTO> {
        val problem: ProblemDTO = createProblem(NOT_FOUND, ex)
        return ResponseEntity.status(problem.status).body(problem)
    }
}
