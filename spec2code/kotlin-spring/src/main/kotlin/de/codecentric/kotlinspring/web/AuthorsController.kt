package de.codecentric.kotlinspring.web

import de.codecentric.kotlinspring.api.AuthorsApi
import de.codecentric.kotlinspring.api.model.AuthorDTO
import de.codecentric.kotlinspring.api.model.CreateAuthorDTO
import de.codecentric.kotlinspring.persistence.Author
import de.codecentric.kotlinspring.persistence.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class AuthorsController(
    val service: AuthorService,
) : AuthorsApi {
    override fun listAuthors(search: String?): ResponseEntity<List<AuthorDTO>> =
        ResponseEntity.ok(service.list(search).map { it.toDto() })

    override fun createAuthor(createAuthorDTO: CreateAuthorDTO): ResponseEntity<AuthorDTO> {
        val author = service.save(createAuthorDTO.toAuthor())
        return ResponseEntity.created(URI.create("/${author.id}")).body(author.toDto())
    }

    override fun getAuthorById(authorId: UUID): ResponseEntity<AuthorDTO> =
        ResponseEntity.ok(service.get(authorId).toDto())

    override fun updateAuthor(
        authorId: UUID,
        createAuthorDTO: CreateAuthorDTO,
    ): ResponseEntity<Unit> {
        service.get(authorId) // check if author exists
        val author = createAuthorDTO.toAuthor(authorId)
        service.save(author)
        return ResponseEntity.noContent().build()
    }

    override fun deleteAuthor(authorId: UUID): ResponseEntity<Unit> {
        service.delete(authorId)
        return ResponseEntity.noContent().build()
    }
}

private fun Author.toDto() = AuthorDTO(id, firstName, lastName)

private fun CreateAuthorDTO.toAuthor() = Author(firstName = firstName, lastName = lastName)

private fun CreateAuthorDTO.toAuthor(id: UUID) = Author(id, firstName, lastName)
