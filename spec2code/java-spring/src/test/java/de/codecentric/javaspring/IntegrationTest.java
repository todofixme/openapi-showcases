package de.codecentric.javaspring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.BookDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.api.model.CreateBookDTO;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void authorLifecycle() {
        // create Author
        final var createResult = client //
                .post().uri("/api/v1/authors") //
                .contentType(APPLICATION_JSON) //
                .bodyValue(new CreateAuthorDTO("John", "Doe")) //
                .exchange() //
                .expectStatus().isCreated() //
                .returnResult(AuthorDTO.class).getResponseBody().blockFirst();

        assertThat(createResult).isNotNull();
        final UUID id = createResult.getId();

        // update Author
        client //
                .put().uri("/api/v1/authors/" + id) //
                .contentType(APPLICATION_JSON) //
                .bodyValue(new CreateAuthorDTO("Jack", "Donaghy")) //
                .exchange() //
                .expectStatus().isNoContent(); //

        // find Author
        client //
                .get().uri("/api/v1/authors?search=jack") //
                .accept(APPLICATION_JSON) //
                .exchange() //
                .expectAll(spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectHeader().valuesMatch("X-Total-Count", "1"));

        // delete Author
        client //
                .delete().uri("/api/v1/authors/" + id) //
                .exchange() //
                .expectStatus().isNoContent(); //

        // trying to request already deleted Author
        client //
                .get().uri("/api/v1/authors/" + id) //
                .exchange() //
                .expectStatus().isNotFound(); //
    }

    @Test
    public void literatureLifecycle() {
        // create Book
        final var createResult = client //
                .post().uri("/api/v1/literature") //
                .contentType(APPLICATION_JSON) //
                .bodyValue(new CreateBookDTO("Boring Title", List.of(UUID.randomUUID()), "978-3-8668-0192-9", 120)) //
                .exchange() //
                .expectStatus().isCreated() //
                .returnResult(BookDTO.class).getResponseBody().blockFirst();

        assertThat(createResult).isNotNull();
        final UUID id = createResult.getId();

        // update Book
        client //
                .put().uri("/api/v1/literature/" + id) //
                .contentType(APPLICATION_JSON) //
                .bodyValue(new CreateBookDTO("Fancy title", List.of(UUID.randomUUID()), "978-3-8668-0192-9", 120)) //
                .exchange() //
                .expectStatus().isNoContent(); //

        // find Book
        client //
                .get().uri("/api/v1/literature?search=fancy") //
                .accept(APPLICATION_JSON) //
                .exchange() //
                .expectAll(spec -> spec.expectStatus().isOk(),
                        spec -> spec.expectHeader().valuesMatch("X-Total-Count", "1"));

        // delete Book
        client //
                .delete().uri("/api/v1/literature/" + id) //
                .exchange() //
                .expectStatus().isNoContent(); //

        // trying to request already deleted Book
        client //
                .get().uri("/api/v1/literature/" + id) //
                .exchange() //
                .expectStatus().isNotFound(); //
    }
}
