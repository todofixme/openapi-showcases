package de.codecentric.javaspring.web;

import de.codecentric.javaspring.api.AuthorsApi;
import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.persistence.Author;
import de.codecentric.javaspring.persistence.AuthorService;
import de.codecentric.javaspring.persistence.PaginatedResult;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthorsController implements AuthorsApi {
    private final AuthorService service;
    private final DtoMapper mapper;

    @Override
    public ResponseEntity<AuthorDTO> createAuthor(CreateAuthorDTO createAuthor) {
        final Author entity = service.save(mapper.map(createAuthor));
        return ResponseEntity //
                .created(URI.create("/" + entity.id())) //
                .body(mapper.map(entity));
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(UUID authorId) {
        service.delete(authorId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AuthorDTO> getAuthorById(UUID authorId) {
        final Author entity = service.get(authorId);
        return ResponseEntity.ok(mapper.map(entity));
    }

    @Override
    public ResponseEntity<List<AuthorDTO>> listAuthors(Integer page, Integer perPage, String search) {
        final PaginatedResult<Author> listOfAuthors = service.list(search, page, perPage);
        final List<AuthorDTO> authors = listOfAuthors.results().stream().map(mapper::map).toList();
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("X-Total-Count", String.valueOf(listOfAuthors.totalCount()));
        responseHeaders.add("X-Total-Pages", String.valueOf(listOfAuthors.totalPages()));
        responseHeaders.add("X-Per-Page", String.valueOf(perPage));
        responseHeaders.add("X-Current-Page", String.valueOf(page));
        return ResponseEntity.ok().headers(responseHeaders).body(authors);
    }

    @Override
    public ResponseEntity<Void> updateAuthor(UUID authorId, CreateAuthorDTO createAuthor) {
        service.get(authorId); // check if author exists
        service.save(mapper.map(createAuthor));
        return ResponseEntity.noContent().build();
    }
}
