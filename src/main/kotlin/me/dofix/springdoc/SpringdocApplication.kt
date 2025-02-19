package me.dofix.springdoc

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = io.swagger.v3.oas.annotations.info.Info(
        title = "Shelf Registry API",
        version = "0.1.0",
        description = "An REST API to manage authors and books."
    )
)
@SpringBootApplication
class SpringdocApplication

fun main(args: Array<String>) {
    runApplication<SpringdocApplication>(*args)
}
