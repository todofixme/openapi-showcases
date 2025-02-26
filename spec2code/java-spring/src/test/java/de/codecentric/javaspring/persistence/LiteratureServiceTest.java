package de.codecentric.javaspring.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class LiteratureServiceTest {
    private static final List<Genre> GENRES = List.of(Genre.values());
    private static final int SIZE = GENRES.size();
    private static final Random RANDOM = new Random();

    private final LiteratureService cut = new LiteratureService();

    private static Genre randomGenre() {
        return GENRES.get(RANDOM.nextInt(SIZE));
    }

    public static Book randomBook() {
        return randomBook(RandomStringUtils.insecure().nextAlphabetic(20));
    }

    private static Book randomBook(String title) {
        return randomBook(title, UUID.randomUUID());
    }

    private static Book randomBook(String title, UUID authorId) {
        return Book.builder() //
                .id(UUID.randomUUID()) //
                .title(title) //
                .authors(Set.of(authorId, UUID.randomUUID())) //
                .recap(RandomStringUtils.insecure().nextAlphabetic(800)) //
                .genre(randomGenre()) //
                .isbn(RandomStringUtils.insecure().nextNumeric(17)) //
                .pages(RANDOM.nextInt(500)) //
                .build();
    }

    private static JournalText randomJournalText() {
        return randomJournalText(RandomStringUtils.insecure().nextAlphabetic(20));
    }

    private static JournalText randomJournalText(String title) {
        return JournalText.builder() //
                .id(UUID.randomUUID()) //
                .title(title) //
                .authors(Set.of(UUID.randomUUID())) //
                .recap(RandomStringUtils.insecure().nextAlphabetic(800)) //
                .genre(randomGenre()) //
                .issn(RandomStringUtils.insecure().nextNumeric(9)) //
                .journalTitle(RandomStringUtils.insecure().nextAlphabetic(12)) //
                .volume(RANDOM.nextInt(100) + 1) //
                .issue(RANDOM.nextInt(11) + 1) //
                .page(RANDOM.nextInt(500)) //
                .build();
    }

    private static AnthologyText randomAnthologyText() {
        return randomAnthologyText(RandomStringUtils.insecure().nextAlphabetic(20));
    }

    private static AnthologyText randomAnthologyText(String title) {
        return AnthologyText.builder() //
                .id(UUID.randomUUID()) //
                .title(title) //
                .authors(Set.of(UUID.randomUUID())) //
                .recap(RandomStringUtils.insecure().nextAlphabetic(800)) //
                .genre(randomGenre()) //
                .anthologyTitle(RandomStringUtils.insecure().nextNumeric(24)) //
                .editor(RandomStringUtils.insecure().nextAlphabetic(20)) //
                .page(RANDOM.nextInt(500)) //
                .build();
    }

    @Test
    public void getAllLiteratureWorks() {
        createRandomBooks(4);
        createRandomJournalTexts(8);
        createRandomAnthologyTexts(3);

        final PaginatedResult<Literature> result = cut.listAll(null, 1, 10);

        assertThat(result.results()).hasSize(10);
        assertThat(result.totalCount()).isEqualTo(15);
        assertThat(result.totalPages()).isEqualTo(2);
    }

    @Test
    public void getAllLiteratureWorks_sorted() {
        final Book book = randomBook("Kingdom of pigeons");
        final JournalText journal = randomJournalText("Pigeons aren't real");
        final AnthologyText anthology = randomAnthologyText("Seagulls, pigeons, sparrows");

        cut.save(book);
        cut.save(journal);
        cut.save(anthology);

        createRandomBooks(3);
        createRandomJournalTexts(5);
        createRandomAnthologyTexts(2);

        final PaginatedResult<Literature> result = cut.listAll("pigeons", 1, 10);

        assertThat(result.results()).hasSize(3);
        assertThat(result.totalCount()).isEqualTo(3);
        assertThat(result.totalPages()).isEqualTo(1);

        assertThat((List<Literature>) result.results()).containsExactlyInAnyOrder(book, journal, anthology);
    }

    @Test
    public void getAllLiteratureWorks_filtered() {
        final JournalText journalX = randomJournalText("X-rays and their applications");
        final Book bookA = randomBook("A brief history of time");
        final AnthologyText anthologyT = randomAnthologyText("The art of computer programming");

        cut.save(journalX);
        cut.save(bookA);
        cut.save(anthologyT);

        final PaginatedResult<Literature> result = cut.listAll(null, 1, 10);

        assertThat(result.results()).hasSize(3);
        assertThat(result.totalCount()).isEqualTo(3);
        assertThat(result.totalPages()).isEqualTo(1);

        assertThat(result.results().get(0)).isEqualTo(bookA);
        assertThat(result.results().get(1)).isEqualTo(anthologyT);
        assertThat(result.results().get(2)).isEqualTo(journalX);
    }

    @Test
    public void getAllLiteratureWorks_handleEmptyStore() {
        final PaginatedResult<Literature> result = cut.listAll(null, 1, 10);

        assertThat(result.results()).hasSize(0);
        assertThat(result.totalCount()).isEqualTo(0);
        assertThat(result.totalPages()).isEqualTo(0);
    }

    @Test
    public void getAllLiteratureWorks_handleNonExistingPage() {
        createRandomBooks(20);

        final PaginatedResult<Literature> result = cut.listAll(null, 5, 10);

        assertThat(result.results()).hasSize(0);
        assertThat(result.totalCount()).isEqualTo(20);
        assertThat(result.totalPages()).isEqualTo(2);
    }

    @Test
    public void getLiteratureWork() {
        final Book book = randomBook("Famous last words");
        cut.save(book);

        final Literature result = cut.get(book.getId());

        assertThat(result) //
                .isNotNull() //
                .extracting("title").isEqualTo("Famous last words");
    }

    @Test
    public void getLiteratureWork_handleNonExistentEntity() {
        final UUID id = UUID.randomUUID();
        assertThatThrownBy( //
                () -> cut.get(id)) //
                .isInstanceOf(NotFoundException.class) //
                .hasMessage("Literature with ID %s not found.", id);
    }

    @Test
    public void listAllByAuthorId() {
        final UUID authorId = UUID.randomUUID();

        final Book bookS = randomBook("Some random book", authorId);
        final Book bookT = randomBook("The book of books", authorId);
        final Book bookA = randomBook("A book for the ages", authorId);
        cut.save(bookS);
        cut.save(bookT);
        cut.save(bookA);
        createRandomBooks(40);

        PaginatedResult<Literature> result = cut.listAllByAuthorId(authorId, 1, 10);

        assertThat(result.results()).hasSize(3);
        assertThat(result.totalCount()).isEqualTo(3);
        assertThat(result.totalPages()).isEqualTo(1);

        assertThat(result.results().get(0)).isEqualTo(bookA);
        assertThat(result.results().get(1)).isEqualTo(bookS);
        assertThat(result.results().get(2)).isEqualTo(bookT);
    }

    private void createRandomBooks(int count) {
        for (int i = 0; i < count; i++) {
            cut.save(randomBook());
        }
    }

    private void createRandomJournalTexts(int count) {
        for (int i = 0; i < count; i++) {
            cut.save(randomJournalText());
        }
    }

    private void createRandomAnthologyTexts(int count) {
        for (int i = 0; i < count; i++) {
            cut.save(randomAnthologyText());
        }
    }
}
