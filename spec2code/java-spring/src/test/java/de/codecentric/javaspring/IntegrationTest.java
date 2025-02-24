package de.codecentric.javaspring;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
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
    public void authorLifecycle() throws Exception {

        // create Author
        final var createResult = client //
                .post().uri("/api/v1/authors") //
                .contentType(APPLICATION_JSON) //
                .bodyValue(new CreateAuthorDTO("John", "Doe")) //
                .exchange() //
                .expectStatus().isCreated() //
                .returnResult(AuthorDTO.class).getResponseBody().blockFirst();

        final UUID id = createResult.getId();

        // update Author
        client //
                .put().uri("/api/v1/authors/" + id) //
                .contentType(APPLICATION_JSON) //
                .bodyValue(new AuthorDTO("Jack", "Donaghy", id)) //
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
}
