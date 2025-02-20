package me.dofix.springdoc.api.author

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
import me.dofix.springdoc.persistence.Author
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "author", description = "A person who writes books, articles, or literary works in general.")
interface AuthorApi {

    @Operation(
        summary = "Create author.",
        description = "Creates an entry for an author in the database.",
        tags = ["author"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "successful operation",
                responseCode = "201",
                content = [
                    Content(
                        schema = Schema(implementation = Author::class)
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
        value = ["/authors"],
        consumes = ["application/xml", "application/json"],
        produces = ["application/xml", "application/json"],
    )
    fun createAuthor(
        @Parameter(description = "Created author object") @RequestBody author: @Valid CreateAuthorDTO
    ): ResponseEntity<AuthorDTO> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Update author.",
        description = "Updates an already existing author in the database.",
        tags = ["author"]
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
        value = ["/authors/{authorId}"],
        consumes = ["application/xml", "application/json"],
        produces = ["application/xml", "application/json"],
    )
    fun updateAuthor(
        @PathVariable("authorId") authorId: UUID,
        @Parameter(description = "Updated author object") @RequestBody author: @Valid CreateAuthorDTO
    ): ResponseEntity<AuthorDTO> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Get all authors.",
        description = "Returns all authors currently stored in the database.",
        tags = ["author"]
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = [Content(array = ArraySchema(schema = Schema(implementation = AuthorDTO::class)))]
        )]
    )
    @GetMapping(
        value = ["/authors"],
        produces = ["application/xml", "application/json"],
    )
    fun getAllAuthors(): ResponseEntity<List<AuthorDTO>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Find author by ID",
        description = "Returns a single author from the database, identified by its unique ID.",
        tags = ["author"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "successful operation",
                content = [Content(schema = Schema(implementation = AuthorDTO::class))],
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid ID supplied",
                content = [Content(schema = Schema(implementation = Problem400BadRequestDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Author not found",
                content = [Content(schema = Schema(implementation = Problem404NotFoundDTO::class))]
            )]
    )
    @GetMapping(value = ["/authors/{authorId}"], produces = ["application/xml", "application/json"])
    fun getAuthorById(
        @Parameter(description = "ID of author to return", required = true) @PathVariable("authorId") authorId: UUID
    ): ResponseEntity<AuthorDTO> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @Operation(
        summary = "Deletes an author",
        description = "Deletes a single author from the database, identified by its unique ID.",
        tags = ["author"]
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
    @DeleteMapping(value = ["/authors/{authorId}"])
    fun deleteAuthor(
        @Parameter(description = "Author id to delete", required = true) @PathVariable("authorId") authorId: UUID,
    ): ResponseEntity<Void> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
