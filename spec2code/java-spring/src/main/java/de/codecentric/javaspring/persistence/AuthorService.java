package de.codecentric.javaspring.persistence;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final Map<UUID, AuthorEntity> store = new ConcurrentHashMap<>();

    public AuthorEntity get(UUID id) {
        if (!store.containsKey(id)) {
            throw new NotFoundException("Author", id);
        }
        return store.get(id);
    }

    public AuthorEntity save(AuthorEntity author) {
        store.put(author.id(), author);
        return author;
    }

    public void delete(UUID id) {
        if (!store.containsKey(id)) {
            throw new NotFoundException("Author", id);
        }
        store.remove(id);
    }

    public List<AuthorEntity> list(String search) {
        if (search == null) {
            return List.copyOf(store.values());
        }
        return store.values().stream()
                .filter(author -> author.firstName().startsWith(search) || author.lastName().startsWith(search))
                .toList();
    }
}
