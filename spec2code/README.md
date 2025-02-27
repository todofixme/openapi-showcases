# Generating code from specification

## Example specification
All projects should implement the Shelf Patrol API:

* [Swagger Editor](https://editor-next.swagger.io/?url=https://raw.githubusercontent.com/todofixme/openapi-showcases/refs/heads/main/spec2code/api-spec.yaml)
* [API docs by Redocly](https://shelf-patrol.dofix.me/docs/api-spec.html)

This sample API has two resources: authors (simple) and literary works (complex).

Currently in progress:
* generating server stubs
  * [Java, Spring Boot, Maven](./java-spring)
  * [Kotlin, Spring Boot, Gradle](./kotlin-spring)