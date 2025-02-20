package me.dofix.springdoc.api.book

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlRootElement
import me.dofix.springdoc.persistence.Book
import java.util.UUID

@JacksonXmlRootElement(localName = "book")
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
class BookDTO(
    private val id: UUID,
    private val name: String,
    private val authors: List<UUID>,
    private val publisher: UUID,
) {
    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    @Schema(
        example = "4a556026-4bbc-417c-b91e-52a2908cf8e5",
        description = "A Universally Unique Identifier (UUID) to uniquely identify a book."
    )
    fun getId() = id

    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    @Schema(example = "Long story short", description = "The name of the book.")
    fun getName() = name

    @JsonProperty("authors")
    @JacksonXmlProperty(localName = "authors")
    @Schema(example = "Doe", description = "The UUIDs of the authors of the book.")
    fun getAuthors() = authors

    @JsonProperty("publisher")
    @JacksonXmlProperty(localName = "publisher")
    @Schema(example = "4a556026-4bbc-417c-b91e-52a2908cf8e5", description = "The UUID of the publisher of the book.")
    fun getPublisher() = publisher
}

fun bookToDto(book: Book) = BookDTO(book.id, book.name, book.authors.map { it.id }, book.publisher.id)
