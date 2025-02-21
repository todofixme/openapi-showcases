package de.codecentric.javaspring.persistence;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String itemType, UUID id) {
        super(String.format("%s with ID %s not found.", itemType, id));
    }
}
