package de.codecentric.javaspring.persistence;

import java.util.UUID;

public record AuthorEntity(UUID id, String firstName, String lastName) {
    public AuthorEntity(String firstName, String lastName) {
        this(UUID.randomUUID(), firstName, lastName);
    }
}
