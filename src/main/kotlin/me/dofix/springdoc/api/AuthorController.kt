package me.dofix.springdoc.api

import me.dofix.springdoc.persistence.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
class AuthorController(
    val service: AuthorService,
) : AuthorApi {
    override fun createAuthor(author: CreateAuthorDTO): ResponseEntity<AuthorDTO> {
        val authorItem = service.save(author.toAuthor())
        return ResponseEntity.created(URI.create("/${authorItem.id}")).body(authorToDto(authorItem))
    }

    override fun getAllAuthors(): ResponseEntity<List<AuthorDTO>> =
        ResponseEntity.ok(service.list().map { authorToDto(it) })

    override fun getAuthorById(authorId: UUID): ResponseEntity<AuthorDTO> =
        ResponseEntity.ok(authorToDto(service.get(authorId)))

    override fun deleteAuthor(authorId: UUID): ResponseEntity<Void> {
        service.delete(authorId)
        return ResponseEntity.noContent().build()
    }
}
