package me.dofix.springdoc.api.publisher

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlRootElement

@JacksonXmlRootElement(localName = "PublishingHouse")
@XmlRootElement(name = "PublishingHouse")
@Schema(
    name = "PublishingHouse",
    description = "A legal entity who produces (content acquisition, editorial process, design and production) and distributes books, articles, or literary works in general."
)
data class CreatePublishingHouseDTO(override val name: String, val headquarters: String) : CreatePublisherDTO(name) {
    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    @Schema(example = "Pendant Publishing", description = "The name of the publisher.")
    override fun getName() = name

    @JsonProperty("headquarters")
    @JacksonXmlProperty(localName = "headquarters")
    @Schema(example = "600 Madison Ave., New York, NY", description = "The location of the headquarters of the publisher.")
    fun getHeadquarters() = headquarters
}
