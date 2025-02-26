package de.codecentric.javaspring.persistence;

import static java.util.Comparator.comparing;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class LiteratureService {
    private final Map<UUID, Literature> store = new ConcurrentHashMap<>();

    public Literature save(Literature literature) {
        store.put(literature.getId(), literature);
        return literature;
    }

    public Literature get(UUID id) {
        if (!store.containsKey(id)) {
            throw new NotFoundException("Literature", id);
        }
        return store.get(id);
    }

    public void delete(UUID id) {
        if (!store.containsKey(id)) {
            throw new NotFoundException("Literature", id);
        }
        store.remove(id);
    }

    public PaginatedResult<Literature> listAll(String search, int page, int perPage) {
        final List<Literature> allLiterature = List.copyOf(store.values());
        final Stream<Literature> filteredLiterature;
        if (search == null) {
            filteredLiterature = allLiterature.stream();
        } else {
            filteredLiterature = allLiterature.stream()
                    .filter(literature -> literature.getTitle().toLowerCase().contains(search.toLowerCase()));
        }
        final List<Literature> sortedLiterature = filteredLiterature.sorted(comparing(Literature::getTitle)).toList();

        int totalResults = sortedLiterature.size();
        int totalPages = (int) Math.ceil((double) totalResults / perPage);

        final List<Literature> paginatedLiterature = //
                sortedLiterature.stream() //
                        .skip((long) (page - 1) * perPage) //
                        .limit(perPage) //
                        .toList();

        return new PaginatedResult<>(paginatedLiterature, totalResults, totalPages);
    }

    public PaginatedResult<Literature> listAllByAuthorId(UUID authorId, int page, int perPage) {
        final List<Literature> filtered = List.copyOf(store.values()).stream() //
                .filter(literature -> literature.getAuthors().contains(authorId)) //
                .sorted(comparing(Literature::getTitle)).toList();

        final List<Literature> paginated = filtered.stream() //
                .skip((long) (page - 1) * perPage) //
                .limit(perPage) //
                .toList();

        int totalItems = filtered.size();
        int totalPages = (int) Math.ceil((double) totalItems / perPage);

        return new PaginatedResult<>(paginated, totalItems, totalPages);
    }
}
