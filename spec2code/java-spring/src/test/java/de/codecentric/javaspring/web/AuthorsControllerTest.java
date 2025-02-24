package de.codecentric.javaspring.web;

import static de.codecentric.javaspring.persistence.AuthorServiceTest.randomAuthor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.codecentric.javaspring.JavaSpringApplication;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.persistence.Author;
import de.codecentric.javaspring.persistence.AuthorService;
import de.codecentric.javaspring.persistence.PaginatedResult;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ComponentScan(basePackageClasses = { JavaSpringApplication.class })
class AuthorsControllerTest {
    private static final UUID ID = UUID.randomUUID();
    private static final int DEFAULT_PER_PAGE = 10;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthorService service;

    private static List<Author> generateRandomAuthors(int count) {
        return IntStream.range(0, count).mapToObj(i -> randomAuthor()).toList();
    }

    @Test
    public void createAuthor() throws Exception {
        final Author author = new Author(ID, "John", "Doe");
        when(service.save(any())).thenReturn(author);

        final CreateAuthorDTO createAuthor = new CreateAuthorDTO("John", "Doe");
        mockMvc.perform(post("/api/v1/authors") //
                .contentType(APPLICATION_JSON) //
                .content(objectMapper.writeValueAsString(createAuthor))) //
                .andExpect(status().isCreated()) //
                .andExpect(jsonPath("$.id").value(ID.toString())) //
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(service).save(any());
    }

    @Test
    public void listAuthor() throws Exception {
        when(service.list(null, 1, DEFAULT_PER_PAGE))
                .thenReturn(new PaginatedResult<>(generateRandomAuthors(10), 100, 10));

        mockMvc.perform(get("/api/v1/authors") //
                .accept(APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(header().string("X-Total-Count", "100")) //
                .andExpect(header().string("X-Total-Pages", "10")) //
                .andExpect(header().string("X-Per-Page", String.valueOf(DEFAULT_PER_PAGE))) //
                .andExpect(header().string("X-Current-Page", "1")) //
                .andExpect(jsonPath("$.size()").value(10));

        verify(service).list(null, 1, DEFAULT_PER_PAGE);
    }

    @Test
    public void listAuthor_pagination() throws Exception {
        when(service.list(null, 3, 5)).thenReturn(new PaginatedResult<>(generateRandomAuthors(5), 100, 10));

        mockMvc.perform(get("/api/v1/authors?page=3&per_page=5") //
                .accept(APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.size()").value(5));

        verify(service).list(null, 3, 5);
    }

    @Test
    public void listAuthor_search() throws Exception {
        when(service.list("needle", 1, DEFAULT_PER_PAGE))
                .thenReturn(new PaginatedResult<>(generateRandomAuthors(10), 100, 10));

        mockMvc.perform(get("/api/v1/authors?search=needle") //
                .accept(APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.size()").value(10));

        verify(service).list("needle", 1, DEFAULT_PER_PAGE);
    }
}
