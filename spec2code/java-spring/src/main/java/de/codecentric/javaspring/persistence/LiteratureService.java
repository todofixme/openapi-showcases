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

    public PaginatedResult<Literature> list(String search, int page, int perPage) {
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

        int start = (page - 1) * perPage;
        int end = Math.min(start + perPage, totalResults);

        final List<Literature> paginatedLiterature =
                sortedLiterature.subList(Math.min(start, totalResults), Math.min(end, totalResults));

        return new PaginatedResult<>(paginatedLiterature, totalResults, totalPages);
    }
}
