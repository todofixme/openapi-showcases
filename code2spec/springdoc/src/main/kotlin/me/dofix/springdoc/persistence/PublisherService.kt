package me.dofix.springdoc.persistence

import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class PublisherService {
    private val store: MutableMap<UUID, Publisher> = ConcurrentHashMap()

    fun get(id: UUID) = store.getOrElse(id) { throw NotFoundException("Publisher with ID $id not found.") }
    fun save(publisher: Publisher): Publisher {
        store[publisher.id] = publisher
        return publisher
    }

    fun delete(id: UUID) = store.remove(id)
    fun list() = store.values.toList()
}
