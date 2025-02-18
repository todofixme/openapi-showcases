package me.dofix.springdoc.persistence

class NotFoundException(
    message: String?,
) : RuntimeException(message)
