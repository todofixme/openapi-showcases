package de.codecentric.javaspring.persistence;

import java.util.List;

public record PaginatedResult<T>(List<T> results, int totalCount, int totalPages) {
}
