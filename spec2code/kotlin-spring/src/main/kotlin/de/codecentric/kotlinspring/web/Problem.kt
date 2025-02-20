package de.codecentric.kotlinspring.web

import de.codecentric.kotlinspring.api.model.ProblemDTO
import de.codecentric.kotlinspring.common.orElse
import org.springframework.http.HttpStatus

data class Problem(
    val type: String = "about:blank",
    val title: String,
    val status: Int,
    val detail: String? = null,
) {
    fun toDTO(): ProblemDTO = ProblemDTO(type, title, status, detail.orElse { "Something went wrong." })
}

fun createProblem(
    status: HttpStatus,
    ex: Exception,
): Problem = Problem(type = getType(status), title = status.reasonPhrase, status = status.value(), detail = ex.message)

fun createProblem(
    status: Int,
    ex: Exception,
): Problem = Problem(title = HttpStatus.valueOf(status).reasonPhrase, status = status, detail = ex.message)

fun createProblem(
    status: HttpStatus,
    detail: String,
): Problem = Problem(type = getType(status), title = status.reasonPhrase, status = status.value(), detail = detail)

fun getType(status: HttpStatus) =
    "https://www.rfc-editor.org/rfc/rfc9110.html#name-${status.value()}-${status.name.lowercase().replace("_", "-")}"