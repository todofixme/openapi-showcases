package de.codecentric.javaspring;

import de.codecentric.javaspring.web.DtoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoMapperTestConfig {

    @Bean
    public DtoMapper dtoMapper() {
        return Mappers.getMapper(DtoMapper.class);
    }
}
