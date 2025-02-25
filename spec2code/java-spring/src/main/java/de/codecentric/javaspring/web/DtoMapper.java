package de.codecentric.javaspring.web;

import de.codecentric.javaspring.api.model.AnthologyTextDTO;
import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.BookDTO;
import de.codecentric.javaspring.api.model.CreateAnthologyTextDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.api.model.CreateBookDTO;
import de.codecentric.javaspring.api.model.CreateJournalTextDTO;
import de.codecentric.javaspring.api.model.JournalTextDTO;
import de.codecentric.javaspring.api.model.LiteratureRequestDTO;
import de.codecentric.javaspring.api.model.LiteratureResponseDTO;
import de.codecentric.javaspring.persistence.AnthologyText;
import de.codecentric.javaspring.persistence.Author;
import de.codecentric.javaspring.persistence.Book;
import de.codecentric.javaspring.persistence.JournalText;
import de.codecentric.javaspring.persistence.Literature;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DtoMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Author map(CreateAuthorDTO authorDTO);

    Author map(CreateAuthorDTO authorDTO, UUID id);

    Author map(AuthorDTO authorDTO);

    AuthorDTO map(Author author);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Book map(CreateBookDTO bookDTO);

    Book map(CreateBookDTO bookDTO, UUID id);

    Book map(BookDTO bookDTO);

    @Mapping(target = "literatureType", constant = "BOOK")
    BookDTO map(Book book);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    JournalText map(CreateJournalTextDTO journalTextDTO);

    JournalText map(CreateJournalTextDTO journalTextDTO, UUID id);

    JournalText map(JournalTextDTO journalTextDTO);

    @Mapping(target = "literatureType", constant = "JOURNALTEXT")
    JournalTextDTO map(JournalText journalText);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    AnthologyText map(CreateAnthologyTextDTO anthologyTextDTO);

    AnthologyText map(CreateAnthologyTextDTO anthologyTextDTO, UUID id);

    AnthologyText map(AnthologyTextDTO anthologyTextDTO);

    @Mapping(target = "literatureType", constant = "ANTHOLOGYTEXT")
    AnthologyTextDTO map(AnthologyText anthologyText);

    default Literature map(LiteratureRequestDTO literatureRequestDTO) {
        return switch (literatureRequestDTO) {
            case CreateBookDTO bookDTO -> map(bookDTO);
            case CreateJournalTextDTO journalTextDTO -> map(journalTextDTO);
            case CreateAnthologyTextDTO anthologyTextDTO -> map(anthologyTextDTO);
            case null, default -> throw new IllegalArgumentException("Unknown literature type");
        };
    }

    default Literature map(LiteratureRequestDTO literatureRequestDTO, UUID id) {
        return switch (literatureRequestDTO) {
            case CreateBookDTO bookDTO -> map(bookDTO, id);
            case CreateJournalTextDTO journalTextDTO -> map(journalTextDTO, id);
            case CreateAnthologyTextDTO anthologyTextDTO -> map(anthologyTextDTO, id);
            case null, default -> throw new IllegalArgumentException("Unknown literature type");
        };
    }

    default LiteratureResponseDTO map(Literature literature) {
        return switch (literature) {
            case Book book -> map(book);
            case JournalText journalText -> map(journalText);
            case AnthologyText anthologyText -> map(anthologyText);
            case null, default -> throw new IllegalArgumentException("Unknown literature type");
        };
    }
}
