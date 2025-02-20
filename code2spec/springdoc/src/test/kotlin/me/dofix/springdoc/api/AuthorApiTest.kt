package me.dofix.springdoc.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.called
import io.mockk.every
import io.mockk.verify
import me.dofix.springdoc.persistence.Author
import me.dofix.springdoc.persistence.AuthorService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class AuthorApiTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var authorService: AuthorService

    @Test
    fun `should create author`() {
        val author = Author(firstName = "John", lastName = "Doe")
        every { authorService.save(any()) } returns author

        mockMvc.perform(
            post("/authors")
                .contentType(APPLICATION_JSON)
                .content("""{"firstName": "John", "lastName": "Doe"}""")
        )
            .andExpect(status().isCreated)

        verify { authorService.save(any()) }
    }

    @Test
    fun `should fail to create author if request was invalid`() {
        val author = Author(firstName = "John", lastName = "Doe")
        every { authorService.save(any()) } returns author

        mockMvc.perform(
            post("/authors")
                .contentType(APPLICATION_JSON)
                .content("""{"name": "John Doe"}""")
        )
            .andExpect(status().isBadRequest)

        verify { authorService.save(any()) wasNot called }
    }
}
