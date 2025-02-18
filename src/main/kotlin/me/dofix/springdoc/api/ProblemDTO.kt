package me.dofix.springdoc.api

import org.springframework.http.HttpStatus

data class ProblemDTO(
    val type: String = "about:blank",
    val title: String,
    val status: Int,
    val detail: String? = null,
)

fun createProblem(
    status: HttpStatus,
    ex: Exception,
): ProblemDTO = ProblemDTO(title = status.reasonPhrase, status = status.value(), detail = ex.message)
