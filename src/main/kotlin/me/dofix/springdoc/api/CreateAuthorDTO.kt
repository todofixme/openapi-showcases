package me.dofix.springdoc.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlRootElement

@JacksonXmlRootElement(localName = "author")
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
class CreateAuthorDTO(
    private val firstName: String,
    private val lastName: String,
) {
    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "firstName")
    @Schema(example = "John", description = "The first name of the author.")
    fun getFirstName() = firstName

    @JsonProperty("lastName")
    @JacksonXmlProperty(localName = "lastName")
    @Schema(example = "Doe", description = "The last name of the author.")
    fun getLastName() = lastName
}
