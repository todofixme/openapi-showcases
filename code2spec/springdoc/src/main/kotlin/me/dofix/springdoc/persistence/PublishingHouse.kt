package me.dofix.springdoc.persistence

import java.util.UUID

data class PublishingHouse(
    override val id: UUID = UUID.randomUUID(),
    override val name: String,
    val headquarters: String,
) : Publisher
