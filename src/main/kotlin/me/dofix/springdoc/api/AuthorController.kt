package me.dofix.springdoc.api

import me.dofix.springdoc.persistence.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
class AuthorController(
    val service: AuthorService,
    val mapper: AuthorMapper,
) : AuthorApi {
    override fun createAuthor(author: CreateAuthorDTO): ResponseEntity<AuthorDTO> {
        val authorItem = service.save(mapper.fromDTO(author))
        return ResponseEntity.created(URI.create("/${authorItem.id}")).body(mapper.toDTO(authorItem))
    }

    override fun getAllAuthors(): ResponseEntity<List<AuthorDTO>> =
        ResponseEntity.ok(service.list().map { mapper.toDTO(it) })

    override fun getAuthorById(authorId: UUID): ResponseEntity<AuthorDTO> =
        ResponseEntity.ok(mapper.toDTO(service.get(authorId)))

    override fun deleteAuthor(authorId: UUID): ResponseEntity<Void> {
        service.delete(authorId)
        return ResponseEntity.noContent().build()
    }
}
