# Generating server code with (Java) spring

This example showcases the usage of [OpenAPI generator spring](https://openapi-generator.tech/docs/generators/spring/) in a Spring Boot / Java / Maven application.

Generation is based on an [OpenAPI spec file](src/main/spec/api-spec.yaml).

## How-to run
```shell
mvn spring-boot:run
```

## How-to test
* use the REST-API with [some example requests](src/test/http/authors.http)
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
