package me.dofix.springdoc.api.publisher

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import me.dofix.springdoc.api.error.Problem400BadRequestDTO
import me.dofix.springdoc.api.error.Problem404NotFoundDTO
import me.dofix.springdoc.api.publisher.PublisherDTO
import me.dofix.springdoc.api.publisher.CreatePublisherDTO
import me.dofix.springdoc.persistence.Publisher
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "publisher", description = "A legal entity who publishes books, articles, or literary works in general.")
interface PublisherApi {

    @Operation(
        summary = "Create publisher.",
        description = "Creates an entry for a publisher in the database.",
        tags = ["publisher"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "successful operation",
                responseCode = "201",
                content = [
                    Content(
                        schema = Schema(implementation = Publisher::class)
                    ),
                ]
            ),
            ApiResponse(
                description = "invalid input",
                responseCode = "400",
                content = [Content(schema = Schema(implementation = Problem400BadRequestDTO::class))]
            ),
        ]
    )
    @PostMapping(
        value = ["/publisher"],
        consumes = ["application/xml", "application/json"],
        produces = ["application/xml", "application/json"],
    )
    fun createPublisher(
        @Parameter(description = "Created publisher object") @RequestBody publisher: @Valid CreatePublisherDTO
    ): ResponseEntity<PublisherDTO> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Update publisher.",
        description = "Updates an already existing publisher in the database.",
        tags = ["publisher"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "successful operation",
                responseCode = "204",
            ),
            ApiResponse(
                description = "invalid input",
                responseCode = "400",
                content = [Content(schema = Schema(implementation = Problem400BadRequestDTO::class))]
            ),
        ]
    )
    @PutMapping(
        value = ["/publishers/{publisherId}"],
        consumes = ["application/xml", "application/json"],
        produces = ["application/xml", "application/json"],
    )
    fun updatePublisher(
        @PathVariable("publisherId") publisherId: UUID,
        @Parameter(description = "Updated publisher object") @RequestBody publisher: @Valid CreatePublisherDTO
    ): ResponseEntity<PublisherDTO> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Get all publishers.",
        description = "Returns all publishers currently stored in the database.",
        tags = ["publisher"]
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = [Content(array = ArraySchema(schema = Schema(implementation = PublisherDTO::class)))]
        )]
    )
    @GetMapping(
        value = ["/publishers"],
        produces = ["application/xml", "application/json"],
    )
    fun getAllPublishers(): ResponseEntity<List<PublisherDTO>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Find publisher by ID",
        description = "Returns a single publisher from the database, identified by its unique ID.",
        tags = ["publisher"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "successful operation",
                content = [Content(schema = Schema(implementation = PublisherDTO::class))],
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = [Content(schema = Schema(implementation = Problem400BadRequestDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Publisher not found",
                content = [Content(schema = Schema(implementation = Problem404NotFoundDTO::class))]
            )]
    )
    @GetMapping(value = ["/publishers/{publisherId}"], produces = ["application/xml", "application/json"])
    fun getPublisherById(
        @Parameter(description = "ID of publisher to return", required = true) @PathVariable("publisherId") publisherId: UUID
    ): ResponseEntity<PublisherDTO> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Deletes an publisher",
        description = "Deletes a single publisher from the database, identified by its unique ID.",
        tags = ["publisher"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "successful operation",
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = [Content(schema = Schema(implementation = Problem400BadRequestDTO::class))]
            )
        ]
    )
    @DeleteMapping(value = ["/publishers/{publisherId}"])
    fun deletePublisher(
        @Parameter(description = "Publisher id to delete", required = true) @PathVariable("publisherId") publisherId: UUID,
    ): ResponseEntity<Void> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
