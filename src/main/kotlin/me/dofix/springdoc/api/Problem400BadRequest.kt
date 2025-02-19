package me.dofix.springdoc.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlRootElement

@JacksonXmlRootElement(localName = "Problem400BadRequest")
@XmlRootElement(name = "Problem400BadRequest")
@Schema(
    name = "Problem400BadRequest",
    description = "The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax). Problem object is based on RFC 7807 (Problem Details for HTTP APIs).",
    externalDocs = ExternalDocumentation(
        url = "https://www.rfc-editor.org/rfc/rfc9110.html#name-400-bad-request",
        description = "RFC 9110, 400 Bad Request"
    )
)
data class Problem400BadRequestDTO(

    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    @Schema(
        example = "https://www.rfc-editor.org/rfc/rfc9110.html#name-400-bad-request",
        description = "Type of problem with URI reference"
    )
    override val type: String,

    @JsonProperty("title")
    @JacksonXmlProperty(localName = "title")
    @Schema(
        example = "Bad Request",
        description = "Error class"
    )
    override val title: String,

    @JsonProperty("status")
    @JacksonXmlProperty(localName = "status")
    @Schema(
        example = "400",
        description = "HTTP status code"
    )
    override val status: Int,

    @JsonProperty("detail")
    @JacksonXmlProperty(localName = "detail")
    @Schema(
        example = "Invalid request",
        description = "Failure message."
    )
    override val detail: String?
) : ProblemDTO(type, title, status, detail)