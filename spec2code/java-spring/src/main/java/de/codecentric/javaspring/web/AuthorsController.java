package de.codecentric.javaspring.web;

import static de.codecentric.javaspring.web.Constants.API_BASE_PATH;

import de.codecentric.javaspring.api.AuthorsApi;
import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.api.model.LiteratureResponseDTO;
import de.codecentric.javaspring.persistence.Author;
import de.codecentric.javaspring.persistence.AuthorService;
import de.codecentric.javaspring.persistence.Literature;
import de.codecentric.javaspring.persistence.LiteratureService;
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
@RequestMapping(API_BASE_PATH)
@AllArgsConstructor
public class AuthorsController implements AuthorsApi {
    private final AuthorService authorService;
    private final LiteratureService literatureService;
    private final DtoMapper mapper;

    @Override
    public ResponseEntity<AuthorDTO> createAuthor(CreateAuthorDTO createAuthor) {
        final Author entity = authorService.save(mapper.map(createAuthor));
        return ResponseEntity //
                .created(URI.create(API_BASE_PATH + "/" + entity.id())) //
                .body(mapper.map(entity));
    }

    @Override
    public ResponseEntity<AuthorDTO> getAuthorById(UUID authorId) {
        final Author entity = authorService.get(authorId);
        return ResponseEntity.ok(mapper.map(entity));
    }

    @Override
    public ResponseEntity<Void> updateAuthor(UUID authorId, CreateAuthorDTO createAuthor) {
        authorService.get(authorId); // check if author exists
        authorService.save(mapper.map(createAuthor, authorId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(UUID authorId) {
        authorService.delete(authorId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<AuthorDTO>> listAuthors(Integer page, Integer perPage, String search) {
        final PaginatedResult<Author> listOfAuthors = authorService.list(search, page, perPage);
        final List<AuthorDTO> authors = listOfAuthors.results().stream().map(mapper::map).toList();
        final HttpHeaders responseHeaders = listOfAuthors.getHeaders();
        responseHeaders.add("X-Per-Page", String.valueOf(perPage));
        responseHeaders.add("X-Current-Page", String.valueOf(page));
        return ResponseEntity.ok().headers(responseHeaders).body(authors);
    }

    @Override
    public ResponseEntity<List<LiteratureResponseDTO>> listAuthorsLiterature(UUID authorId, Integer page,
            Integer perPage) {
        PaginatedResult<Literature> result = literatureService.listAllByAuthorId(authorId, page, perPage);
        final List<LiteratureResponseDTO> literature = result.results().stream().map(mapper::map).toList();
        final HttpHeaders responseHeaders = result.getHeaders();
        responseHeaders.add("X-Per-Page", String.valueOf(perPage));
        responseHeaders.add("X-Current-Page", String.valueOf(page));
        return ResponseEntity.ok().headers(responseHeaders).body(literature);
    }
}
