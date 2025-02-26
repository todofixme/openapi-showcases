package de.codecentric.kotlinspring.web

import de.codecentric.kotlinspring.api.LiteratureApi
import de.codecentric.kotlinspring.api.model.LiteratureRequestDTO
import de.codecentric.kotlinspring.api.model.LiteratureResponseDTO
import de.codecentric.kotlinspring.persistence.LiteratureService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class LiteratureController(
    val service: LiteratureService,
) : LiteratureApi {
    override fun createLiterature(literatureRequestDTO: LiteratureRequestDTO): ResponseEntity<LiteratureResponseDTO> =
        super.createLiterature(literatureRequestDTO)
}
