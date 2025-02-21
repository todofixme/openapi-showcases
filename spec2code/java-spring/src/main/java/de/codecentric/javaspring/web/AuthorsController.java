package de.codecentric.javaspring.web;

import de.codecentric.javaspring.api.AuthorsApi;
import de.codecentric.javaspring.api.model.Author;
import de.codecentric.javaspring.api.model.CreateAuthor;
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
    public ResponseEntity<Author> createAuthor(CreateAuthor createAuthor) {
        final AuthorEntity entity =
                service.save(new AuthorEntity(createAuthor.getFirstName(), createAuthor.getLastName()));
        return ResponseEntity.created(URI.create("/" + entity.id()))
                .body(new Author().id(entity.id()).firstName(entity.firstName()).lastName(entity.lastName()));
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(UUID authorId) {
        service.delete(authorId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Author> getAuthorById(UUID authorId) {
        final AuthorEntity entity = service.get(authorId);
        return ResponseEntity
                .ok(new Author().id(entity.id()).firstName(entity.firstName()).lastName(entity.lastName()));
    }

    @Override
    public ResponseEntity<List<Author>> listAuthors(String search) {
        final List<Author> authors = service.list(search).stream()
                .map(entity -> new Author().id(entity.id()).firstName(entity.firstName()).lastName(entity.lastName()))
                .toList();
        return ResponseEntity.ok(authors);
    }

    @Override
    public ResponseEntity<Void> updateAuthor(UUID authorId, CreateAuthor createAuthor) {
        service.get(authorId); // check if author exists
        service.save(new AuthorEntity(authorId, createAuthor.getFirstName(), createAuthor.getLastName()));
        return ResponseEntity.noContent().build();
    }
}
