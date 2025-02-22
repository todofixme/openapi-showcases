package de.codecentric.javaspring.persistence;

import java.util.UUID;

public record Author(UUID id, String firstName, String lastName) {
    public Author(String firstName, String lastName) {
        this(UUID.randomUUID(), firstName, lastName);
    }
}
