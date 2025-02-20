package me.dofix.springdoc.persistence

import java.util.UUID

data class Book(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val authors: Set<Author>,
    val publisher: Publisher,
)
