package de.codecentric.kotlinspring.persistence

import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class AuthorService {
    private val store: MutableMap<UUID, Author> = ConcurrentHashMap()

    fun get(id: UUID) = store.getOrElse(id) { throw NotFoundException("Author with ID $id not found.") }

    fun save(author: Author): Author {
        store[author.id] = author

        return author
    }

    fun delete(id: UUID) = store.remove(id) ?: throw NotFoundException("Author with ID $id not found.")

    fun list(search: String?): List<Author> =
        search?.let {
            store.values
                .toList()
                .filter { it.firstName.startsWith(search) || it.lastName.startsWith(search) }
        } ?: store.values.toList()
}
