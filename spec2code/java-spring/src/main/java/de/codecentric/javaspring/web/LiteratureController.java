package de.codecentric.javaspring.web;

import static de.codecentric.javaspring.web.Constants.API_BASE_PATH;

import de.codecentric.javaspring.api.LiteratureApi;
import de.codecentric.javaspring.api.model.LiteratureRequestDTO;
import de.codecentric.javaspring.api.model.LiteratureResponseDTO;
import de.codecentric.javaspring.persistence.Literature;
import de.codecentric.javaspring.persistence.LiteratureService;
import de.codecentric.javaspring.persistence.PaginatedResult;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_BASE_PATH)
@AllArgsConstructor
public class LiteratureController implements LiteratureApi {
    private final LiteratureService service;
    private final DtoMapper mapper;

    @Override
    public ResponseEntity<LiteratureResponseDTO> createLiterature(LiteratureRequestDTO literatureRequestDTO) {
        final Literature entity = service.save(mapper.map(literatureRequestDTO));
        return ResponseEntity //
                .created(URI.create(API_BASE_PATH + "/" + entity.getId())) //
                .body(mapper.map(entity));
    }

    @Override
    public ResponseEntity<LiteratureResponseDTO> getLiteratureById(UUID literatureId) {
        final Literature entity = service.get(literatureId);
        return ResponseEntity.ok(mapper.map(entity));
    }

    @Override
    public ResponseEntity<Void> updateLiterature(UUID literatureId, LiteratureRequestDTO literatureRequestDTO) {
        service.get(literatureId); // check if literature exists
        service.save(mapper.map(literatureRequestDTO, literatureId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteLiterature(UUID literatureId) {
        service.delete(literatureId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<LiteratureResponseDTO>> listLiterature(Integer page, Integer perPage, String search) {
        final PaginatedResult<Literature> results = service.listAll(search, page, perPage);
        final List<LiteratureResponseDTO> literature = results.results().stream().map(mapper::map).toList();
        final HttpHeaders responseHeaders = results.getHeaders();
        responseHeaders.add("X-Per-Page", String.valueOf(perPage));
        responseHeaders.add("X-Current-Page", String.valueOf(page));
        return ResponseEntity.ok().headers(responseHeaders).body(literature);
    }
}
