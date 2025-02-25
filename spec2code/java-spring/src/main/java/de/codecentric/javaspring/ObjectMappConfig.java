package de.codecentric.javaspring;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.codecentric.javaspring.api.model.AnthologyTextDTO;
import de.codecentric.javaspring.api.model.BookDTO;
import de.codecentric.javaspring.api.model.CreateAnthologyTextDTO;
import de.codecentric.javaspring.api.model.CreateBookDTO;
import de.codecentric.javaspring.api.model.CreateJournalTextDTO;
import de.codecentric.javaspring.api.model.JournalTextDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMappConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper() //
                // This is a workaround to fix the wrong @JsonTypeName annotations
                // https://github.com/OpenAPITools/openapi-generator/issues/17343
                .addMixIn(BookDTO.class, BookTypeMixIn.class) //
                .addMixIn(CreateBookDTO.class, BookTypeMixIn.class) //
                .addMixIn(JournalTextDTO.class, JournalTextTypeMixIn.class) //
                .addMixIn(CreateJournalTextDTO.class, JournalTextTypeMixIn.class) //
                .addMixIn(AnthologyTextDTO.class, AnthologyTextTypeMixIn.class) //
                .addMixIn(CreateAnthologyTextDTO.class, AnthologyTextTypeMixIn.class);
    }

    @JsonTypeName("BOOK")
    public abstract static class BookTypeMixIn {}

    @JsonTypeName("JOURNALTEXT")
    public abstract static class JournalTextTypeMixIn {}

    @JsonTypeName("ANTHOLOGYTEXT")
    public abstract static class AnthologyTextTypeMixIn {}
}
