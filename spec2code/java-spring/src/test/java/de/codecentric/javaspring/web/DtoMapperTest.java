package de.codecentric.javaspring.web;

import static org.assertj.core.api.Assertions.assertThat;

import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.persistence.Author;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class DtoMapperTest {
    private final DtoMapper mapper = Mappers.getMapper(DtoMapper.class);

    @Test
    void fromCreateAuthorDto() {
        final CreateAuthorDTO dto = new CreateAuthorDTO("John", "Doe");

        final Author entity = mapper.map(dto);

        assertThat(entity) //
                .isNotNull() //
                .extracting("firstName", "lastName") //
                .containsExactly("John", "Doe");
        assertThat(entity.id()).isNotNull();
    }

    @Test
    void fromCreateAuthorDto_withId() {
        final UUID id = UUID.randomUUID();
        final CreateAuthorDTO dto = new CreateAuthorDTO("John", "Doe");

        final Author entity = mapper.map(dto, id);

        assertThat(entity.id()).isEqualTo(id);
    }

    @Test
    void fromAuthorDto() {
        final AuthorDTO dto = new AuthorDTO("John", "Doe", UUID.randomUUID());

        final Author entity = mapper.map(dto);

        assertThat(entity) //
                .isNotNull() //
                .usingRecursiveComparison() //
                .isEqualTo(dto);
    }

    @Test
    void toAuthorDto() {
        final Author entity = new Author("John", "Doe");

        final AuthorDTO dto = mapper.map(entity);

        assertThat(dto) //
                .isNotNull() //
                .usingRecursiveComparison() //
                .isEqualTo(entity);
    }
}
