package de.codecentric.javaspring.persistence;

import java.util.Set;
import java.util.UUID;

public interface Literature {
    UUID getId();

    String getTitle();

    Set<UUID> getAuthors();

    String getRecap();

    Genre getGenre();
}
