# Generating server code with (Java) spring

This example showcases the usage of [OpenAPI generator spring](https://openapi-generator.tech/docs/generators/spring/) in a Spring Boot / Java / Maven application.

Generation is based on an [OpenAPI spec file](src/main/spec/api-spec.yaml).

## Current state

- [x] simple model
- [x] simple path
- [x] complex model
- [x] enums
- [x] inheritence
- [x] complex paths
- [x] header
- [x] references
- [ ] security

## How-to run
```shell
mvn spring-boot:run
```

## How-to test
* use the REST-API with [some example](src/test/http/authors.http) [requests](src/test/http/literature.http)
* run all tests:
```shell
mvn test
```
## API documentation
* Generate the API documentation with [Redocly CLI](https://redocly.com/docs/cli/commands/build-docs):
```shell
mvn clean verify -DskipTests -PdocGen
```
* Run the application and access the [API documentation](http://localhost:7003/docs/api-spec.html)

## Run as Docker container
```shell
mvn spring-boot:build-image
docker run --rm --name sp-java-spring -p 7003:7003 todofixme/shelf-patrol-java-spring
```
