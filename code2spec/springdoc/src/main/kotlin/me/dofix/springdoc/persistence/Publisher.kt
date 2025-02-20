package me.dofix.springdoc.persistence

import java.util.UUID

interface Publisher {
    val id: UUID
    val name: String
}
