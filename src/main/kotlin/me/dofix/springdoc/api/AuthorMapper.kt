package me.dofix.springdoc.api

import me.dofix.springdoc.persistence.Author
import org.springframework.stereotype.Component

@Component
class AuthorMapper {
    fun toDTO(author: Author): AuthorDTO {
        return AuthorDTO(
            id = author.id,
            firstName = author.firstName,
            lastName = author.lastName
        )
    }

    fun fromDTO(dto: CreateAuthorDTO): Author {
        return Author(
            firstName = dto.getFirstName(),
            lastName = dto.getLastName()
        )
    }
}
