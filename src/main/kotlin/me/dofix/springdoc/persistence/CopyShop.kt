package me.dofix.springdoc.persistence

import java.util.UUID

data class CopyShop(
    override val id: UUID = UUID.randomUUID(),
    override val name: String,
    val location: String,
) : Publisher
