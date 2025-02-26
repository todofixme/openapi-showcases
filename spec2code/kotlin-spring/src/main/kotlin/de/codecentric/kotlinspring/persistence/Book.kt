package de.codecentric.kotlinspring.persistence

import java.util.UUID

data class Book(
    override val id: UUID,
    override val title: String,
    override val authors: Set<UUID>,
    override val recap: String?,
    override val genre: Genre?,
    val isbn: String,
    val pages: Int,
) : Literature(id, title, authors, recap, genre)
