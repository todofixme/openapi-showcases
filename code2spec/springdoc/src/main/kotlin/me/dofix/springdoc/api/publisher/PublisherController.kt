package me.dofix.springdoc.api.publisher

import me.dofix.springdoc.api.author.PublisherDTO
import me.dofix.springdoc.api.author.CreatePublisherDTO
import me.dofix.springdoc.api.author.authorToDto
import me.dofix.springdoc.persistence.PublisherService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
class PublisherController(
    val service: PublisherService,
) : PublisherApi {
    override fun createPublisher(publisher: CreatePublisherDTO): ResponseEntity<PublisherDTO> {
        val publisherItem = service.save(publisher)
        return ResponseEntity.created(URI.create("/${publisherItem.id}")).body(publisherToDto(publisherItem))
    }

    override fun updatePublisher(
        publisherId: UUID,
        publisher: CreatePublisherDTO,
    ): ResponseEntity<PublisherDTO> {
        service.save(publisher.toPublisher(publisherId))
        return ResponseEntity.noContent().build()
    }

    override fun getAllPublishers(): ResponseEntity<List<PublisherDTO>> =
        ResponseEntity.ok(service.list().map { publisherToDto(it) })

    override fun getPublisherById(publisherId: UUID): ResponseEntity<PublisherDTO> =
        ResponseEntity.ok(publisherToDto(service.get(publisherId)))

    override fun deletePublisher(publisherId: UUID): ResponseEntity<Void> {
        service.delete(publisherId)
        return ResponseEntity.noContent().build()
    }
}
