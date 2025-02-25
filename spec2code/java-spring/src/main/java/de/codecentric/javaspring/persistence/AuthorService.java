package de.codecentric.javaspring.persistence;

import static java.util.Comparator.comparing;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final Map<UUID, Author> store = new ConcurrentHashMap<>();

    public Author save(Author author) {
        store.put(author.id(), author);
        return author;
    }

    public Author get(UUID id) {
        if (!store.containsKey(id)) {
            throw new NotFoundException("Author", id);
        }
        return store.get(id);
    }

    public void delete(UUID id) {
        if (!store.containsKey(id)) {
            throw new NotFoundException("Author", id);
        }
        store.remove(id);
    }

    public PaginatedResult<Author> list(String search, int page, int perPage) {
        final List<Author> allAuthors = List.copyOf(store.values());
        final Stream<Author> filteredAuthors;
        if (search == null) {
            filteredAuthors = allAuthors.stream();
        } else {
            filteredAuthors = allAuthors.stream()
                    .filter(author -> author.firstName().toLowerCase().startsWith(search.toLowerCase())
                            || author.lastName().toLowerCase().startsWith(search.toLowerCase()));
        }
        final List<Author> sortedAuthors = filteredAuthors.sorted(comparing(Author::lastName)).toList();

        int totalAuthors = sortedAuthors.size();
        int totalPages = (int) Math.ceil((double) totalAuthors / perPage);

        int start = (page - 1) * perPage;
        int end = Math.min(start + perPage, totalAuthors);

        final List<Author> paginatedAuthors =
                sortedAuthors.subList(Math.min(start, totalAuthors), Math.min(end, totalAuthors));

        return new PaginatedResult<>(paginatedAuthors, totalAuthors, totalPages);
    }
}
