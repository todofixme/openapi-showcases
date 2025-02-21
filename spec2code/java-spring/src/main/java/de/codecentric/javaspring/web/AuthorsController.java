package de.codecentric.javaspring.web;

import de.codecentric.javaspring.api.AuthorsApi;
import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.persistence.AuthorEntity;
import de.codecentric.javaspring.persistence.AuthorService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthorsController implements AuthorsApi {
    private final AuthorService service;

    @Override
    public ResponseEntity<AuthorDTO> createAuthor(CreateAuthorDTO createAuthor) {
        final AuthorEntity entity =
                service.save(new AuthorEntity(createAuthor.getFirstName(), createAuthor.getLastName()));
        return ResponseEntity.created(URI.create("/" + entity.id()))
                .body(new AuthorDTO().id(entity.id()).firstName(entity.firstName()).lastName(entity.lastName()));
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(UUID authorId) {
        service.delete(authorId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AuthorDTO> getAuthorById(UUID authorId) {
        final AuthorEntity entity = service.get(authorId);
        return ResponseEntity
                .ok(new AuthorDTO().id(entity.id()).firstName(entity.firstName()).lastName(entity.lastName()));
    }

    @Override
    public ResponseEntity<List<AuthorDTO>> listAuthors(String search) {
        final List<AuthorDTO> authors = service.list(search).stream().map(
                entity -> new AuthorDTO().id(entity.id()).firstName(entity.firstName()).lastName(entity.lastName()))
                .toList();
        return ResponseEntity.ok(authors);
    }

    @Override
    public ResponseEntity<Void> updateAuthor(UUID authorId, CreateAuthorDTO createAuthor) {
        service.get(authorId); // check if author exists
        service.save(new AuthorEntity(authorId, createAuthor.getFirstName(), createAuthor.getLastName()));
        return ResponseEntity.noContent().build();
    }
}
