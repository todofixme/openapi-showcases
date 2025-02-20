package me.dofix.springdoc.api.publisher

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlRootElement

@JacksonXmlRootElement(localName = "CopyShop")
@XmlRootElement(name = "CopyShop")
@Schema(
    name = "CopyShop",
    description = "A local business that print copies of books for individuals and other businesses."
)
data class CreateCopyShopDTO(override val name: String, val location: String) : CreatePublisherDTO(name){
    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    @Schema(example = "Copy That!", description = "The name of the copy shop.")
    override fun getName() = name

    @JsonProperty("location")
    @JacksonXmlProperty(localName = "location")
    @Schema(example = "129 West 81st Street, New York, NY", description = "The location of the copy shop.")
    fun getLocation() = location
}
