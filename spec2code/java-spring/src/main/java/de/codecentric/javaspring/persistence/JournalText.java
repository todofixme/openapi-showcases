package de.codecentric.javaspring.persistence;

import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter(AccessLevel.PRIVATE)
public class JournalText implements Literature {
    private final UUID id;
    private final String title;
    private final Set<UUID> authors;
    private final String recap;
    private final Genre genre;
    private final String issn;
    private final String journalTitle;
    private final int volume;
    private final int issue;
    private final int page;
}
