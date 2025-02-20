package me.dofix.springdoc.api.publisher

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlRootElement

@JacksonXmlRootElement(localName = "publisher")
@XmlRootElement(name = "publisher")
@Schema(
    name = "Publisher",
    description = "A legal entity who publishes books, articles, or literary works in general."
)
open class CreatePublisherDTO(open val name: String) {
    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    @Schema(example = "Pendant Publishing", description = "The name of the publisher.")
    open fun getName() = name
}