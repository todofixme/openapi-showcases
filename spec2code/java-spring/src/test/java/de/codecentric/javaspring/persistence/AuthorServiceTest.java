package de.codecentric.javaspring.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class AuthorServiceTest {
    private final AuthorService cut = new AuthorService();

    public static Author randomAuthor() {
        return randomAuthor("");
    }

    public static Author randomAuthor(String namePrefix) {
        return new Author(RandomStringUtils.insecure().nextAlphabetic(8),
                namePrefix + RandomStringUtils.insecure().nextAlphabetic(8));
    }

    @Test
    public void getAllAuthors() {
        createRandomAuthors(8);

        final PaginatedResult<Author> result = cut.list(null, 1, 100);

        assertThat(result.results()).hasSize(8);
        assertThat(result.totalCount()).isEqualTo(8);
        assertThat(result.totalPages()).isEqualTo(1);
    }

    @Test
    public void getAllAuthors_sorted() {
        final Author needle = cut.save(new Author("John", "Doe"));
        createRandomAuthors(16, "A");
        createRandomAuthors(15, "Z");

        final PaginatedResult<Author> result = cut.list(null, 6, 3);

        assertThat(result.results()).hasSize(3);
        assertThat(result.totalCount()).isEqualTo(32);
        assertThat(result.totalPages()).isEqualTo(11);

        assertThat(result.results().get(0).lastName()).startsWith("A");
        assertThat(result.results().get(1)).isEqualTo(needle);
        assertThat(result.results().get(2).lastName()).startsWith("Z");
    }

    @Test
    public void getAllAuthors_caseInsensitive() {
        final Author needle = cut.save(new Author("John", "Doe"));
        createRandomAuthors(20);

        final PaginatedResult<Author> result = cut.list("john", 1, 10);

        assertThat(result.results()).hasSize(1);
        assertThat(result.results().getFirst().firstName()).isEqualTo("John");
    }

    @Test
    public void getAllAuthors_filtered() {
        final Author needle1 = cut.save(new Author("John", "Doe"));
        final Author needle2 = cut.save(new Author("John", "Wick"));
        final Author needle3 = cut.save(new Author("John", "McClane"));
        final Author needle4 = cut.save(new Author("Little", "John"));
        createRandomAuthors(100);

        final PaginatedResult<Author> result = cut.list("John", 1, 5);

        assertThat(result.results()).hasSize(4);
        assertThat(result.totalCount()).isEqualTo(4);
        assertThat(result.totalPages()).isEqualTo(1);

        assertThat(result.results()).containsExactlyInAnyOrder(needle1, needle2, needle3, needle4);
    }

    @Test
    public void getAllAuthors_handleEmptyStore() {
        final PaginatedResult<Author> result = cut.list(null, 1, 10);

        assertThat(result.results()).hasSize(0);
        assertThat(result.totalCount()).isEqualTo(0);
        assertThat(result.totalPages()).isEqualTo(0);
    }

    @Test
    public void getAllAuthors_handleNonExistingPage() {
        createRandomAuthors(20);

        final PaginatedResult<Author> result = cut.list(null, 5, 10);

        assertThat(result.results()).hasSize(0);
        assertThat(result.totalCount()).isEqualTo(20);
        assertThat(result.totalPages()).isEqualTo(2);
    }

    private void createRandomAuthors(int x) {
        for (int i = 0; i < x; i++) {
            cut.save(randomAuthor());
        }
    }

    private void createRandomAuthors(int x, String prefix) {
        for (int i = 0; i < x; i++) {
            cut.save(randomAuthor(prefix));
        }
    }
}
