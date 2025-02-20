package me.dofix.springdoc.persistence

import java.util.UUID

data class Author(
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
)
