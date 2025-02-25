package de.codecentric.javaspring.persistence;

import java.util.List;
import org.springframework.http.HttpHeaders;

public record PaginatedResult<T>(List<? extends T> results, int totalCount, int totalPages) {
    public HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(totalCount));
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        return headers;
    }
}
