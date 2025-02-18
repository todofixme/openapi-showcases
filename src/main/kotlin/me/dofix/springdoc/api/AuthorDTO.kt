package me.dofix.springdoc.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlRootElement
import java.util.UUID

@JacksonXmlRootElement(localName = "author")
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
class AuthorDTO(
    private val id: UUID,
    private val firstName: String,
    private val lastName: String,
) {
    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    @Schema(
        example = "4a556026-4bbc-417c-b91e-52a2908cf8e5",
        description = "A Universally Unique Identifier (UUID) to uniquely identify an author."
    )
    fun getId() = id

    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "firstName")
    @Schema(example = "John", description = "The first name of the author.")
    fun getFirstName() = firstName

    @JsonProperty("lastName")
    @JacksonXmlProperty(localName = "lastName")
    @Schema(example = "Doe", description = "The last name of the author.")
    fun getLastName() = lastName
}
