package me.dofix.springdoc.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlRootElement
import org.springframework.http.HttpStatus

@JacksonXmlRootElement(localName = "problem")
@XmlRootElement(name = "problem")
@Schema(
    name = "Problem",
    description = "Problem details based on RFC 7807"
)
data class ProblemDTO(

    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    @Schema(
        example = "https://www.rfc-editor.org/rfc/rfc9110.html#name-400-bad-request",
        description = "Type of problem with URI reference"
    )
    val type: String = "about:blank",

    @JsonProperty("title")
    @JacksonXmlProperty(localName = "title")
    @Schema(
        example = "Bad Request",
        description = "Error class"
    )
    val title: String,

    @JsonProperty("status")
    @JacksonXmlProperty(localName = "status")
    @Schema(
        example = "400",
        description = "HTTP status code"
    )
    val status: Int,

    @JsonProperty("detail")
    @JacksonXmlProperty(localName = "detail")
    @Schema(
        example = "Invalid request",
        description = "Failure message."
    )
    val detail: String? = null,
)

fun createProblem(
    status: HttpStatus,
    ex: Exception,
): ProblemDTO = ProblemDTO(title = status.reasonPhrase, status = status.value(), detail = ex.message)

fun createProblem(
    status: Int,
    ex: Exception,
): ProblemDTO = ProblemDTO(title = HttpStatus.valueOf(status).reasonPhrase, status = status, detail = ex.message)

fun createProblem(
    status: HttpStatus,
    detail: String,
): ProblemDTO = ProblemDTO(title = status.reasonPhrase, status = status.value(), detail = detail)
