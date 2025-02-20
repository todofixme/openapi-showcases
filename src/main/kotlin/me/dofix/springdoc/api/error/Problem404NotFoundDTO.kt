package me.dofix.springdoc.api.error

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlRootElement

@JacksonXmlRootElement(localName = "Problem400BadRequest")
@XmlRootElement(name = "Problem400BadRequest")
@Schema(
    name = "Problem404NotFound",
    description = "The origin server did not find a current representation for the target resource. Problem object is based on RFC 7807 (Problem Details for HTTP APIs).",
    externalDocs = ExternalDocumentation(
        url = "https://www.rfc-editor.org/rfc/rfc9110.html#name-404-not-found",
        description = "RFC 9110, 404 Bad Request"
    )
)
data class Problem404NotFoundDTO(

    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    @Schema(
        example = "https://www.rfc-editor.org/rfc/rfc9110.html#name-404-not-found",
        description = "Type of problem with URI reference"
    )
    override val type: String,

    @JsonProperty("title")
    @JacksonXmlProperty(localName = "title")
    @Schema(
        example = "Not Found",
        description = "Error class"
    )
    override val title: String,

    @JsonProperty("status")
    @JacksonXmlProperty(localName = "status")
    @Schema(
        example = "404",
        description = "HTTP status code"
    )
    override val status: Int,

    @JsonProperty("detail")
    @JacksonXmlProperty(localName = "detail")
    @Schema(
        example = "Item not found",
        description = "Failure message."
    )
    override val detail: String?
) : ProblemDTO(type, title, status, detail)