package me.dofix.springdoc

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest(@Autowired val testClient: WebTestClient) {
    @Test
    fun `should create author`() {
        testClient
            .post()
            .uri("/authors")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .bodyValue("""{"firstName": "John", "lastName": "Doe"}""")
            .exchange()
            .expectStatus()
            .isCreated
            .expectBody()
            .jsonPath("$.firstName").isEqualTo("John")
            .jsonPath("$.lastName").isEqualTo("Doe")
            .returnResult()
    }
}
