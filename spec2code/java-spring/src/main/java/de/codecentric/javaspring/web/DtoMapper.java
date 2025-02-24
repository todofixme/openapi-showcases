package de.codecentric.javaspring.web;

import de.codecentric.javaspring.api.model.AuthorDTO;
import de.codecentric.javaspring.api.model.CreateAuthorDTO;
import de.codecentric.javaspring.persistence.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DtoMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Author map(CreateAuthorDTO authorDTO);

    Author map(AuthorDTO authorDTO);

    AuthorDTO map(Author author);
}
