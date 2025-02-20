package me.dofix.springdoc.persistence

import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class BookService(val authorService: AuthorService, val publisherService: PublisherService) {
    private val store: MutableMap<UUID, Book> = ConcurrentHashMap()

    fun get(id: UUID) = store.getOrElse(id) { throw NotFoundException("Book with ID $id not found.") }
    fun save(book: Book): Book {
        store[book.id] = book
        return book
    }

    fun addAuthors(bookId: UUID, authorIds: List<UUID>) {
        val book = get(bookId)
        authorIds.forEach {
            val author = authorService.get(it)
            store[bookId] = book.copy(authors = book.authors + author)
        }
    }

    fun addPublisher(bookId: UUID, publisherId: UUID) {
        val book = get(bookId)
        val publisher = publisherService.get(publisherId)
        store[bookId] = book.copy(publisher = publisher)
    }

    fun delete(id: UUID) = store.remove(id)
    fun list() = store.values.toList()
}
