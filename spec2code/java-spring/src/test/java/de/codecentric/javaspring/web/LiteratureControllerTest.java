package de.codecentric.javaspring.web;

import static de.codecentric.javaspring.persistence.LiteratureServiceTest.randomBook;
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
import de.codecentric.javaspring.api.model.CreateBookDTO;
import de.codecentric.javaspring.persistence.Book;
import de.codecentric.javaspring.persistence.LiteratureService;
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
public class LiteratureControllerTest {
    private static final UUID ID = UUID.randomUUID();
    private static final int DEFAULT_PER_PAGE = 10;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LiteratureService service;

    private static List<Book> generateRandomBooks(int count) {
        return IntStream.range(0, count).mapToObj(i -> randomBook()).toList();
    }

    @Test
    public void createBook() throws Exception {
        final Book book = Book.builder().id(ID).title("Fine Title").build();
        when(service.save(any())).thenReturn(book);

        final CreateBookDTO createBook =
                new CreateBookDTO("Fine Title", List.of(UUID.randomUUID()), "978-3-8668-0192-9", 120);
        mockMvc.perform(post("/api/v1/literature") //
                .contentType(APPLICATION_JSON) //
                .content(objectMapper.writeValueAsString(createBook))) //
                .andExpect(status().isCreated()) //
                .andExpect(jsonPath("$.id").value(ID.toString())) //
                .andExpect(jsonPath("$.title").value("Fine Title"));

        verify(service).save(any());
    }

    @Test
    public void listLiterature() throws Exception {
        when(service.list(null, 1, DEFAULT_PER_PAGE))
                .thenReturn(new PaginatedResult<>(generateRandomBooks(10), 100, 10));

        mockMvc.perform(get("/api/v1/literature") //
                .accept(APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(header().string("X-Total-Count", "100")) //
                .andExpect(header().string("X-Total-Pages", "10")) //
                .andExpect(header().string("X-Per-Page", String.valueOf(DEFAULT_PER_PAGE))) //
                .andExpect(header().string("X-Current-Page", "1")) //
                .andExpect(jsonPath("$.size()").value(10));

        verify(service).list(null, 1, DEFAULT_PER_PAGE);
    }
}
