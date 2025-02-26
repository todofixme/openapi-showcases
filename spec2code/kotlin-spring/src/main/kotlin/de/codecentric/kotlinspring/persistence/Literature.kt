package de.codecentric.kotlinspring.persistence

import java.util.UUID

open class Literature(
    open val id: UUID,
    open val title: String,
    open val authors: Set<UUID>,
    open val recap: String? = null,
    open val genre: Genre? = null,
)

enum class Genre(
    val title: String,
) {
    FICTION("Fiction"),
    NON_FICTION("Non Fiction"),
    POETRY("Poetry"),
    DRAMA("Drama"),
    PROSE("Prose"),
    ESSAY("Essay"),
    SCIENCE("Scientific Article"),
    LIFESTYLE("Lifestyle Article"),
    SPORTS("Sports News"),
}
