package de.codecentric.kotlinspring.persistence

import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class LiteratureService {
    private val store: MutableMap<UUID, Literature> = ConcurrentHashMap()

    fun save(literature: Literature): Literature {
        store[literature.id] = literature

        return literature
    }

    fun list(): List<Literature> = store.values.toList()
}
