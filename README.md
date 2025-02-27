# OpenAPI generator showcases

[OpenAPI Specification](https://swagger.io/specification/) is the most popular and widly used API description language. Generation helps to keep specification and source code in sync.

These showcases demonstrate, how different OpenAPI generators might help to build stable REST APIs.

We'll focus on these functionallities:
* simple model
* simple path
* complex model
  * enums
  * inheritence
* complex Paths
  * header
  * references
* security

### Currently in progress
* spec to code
  * [Example Specification](./spec2code)
  * [Java, Spring Boot, Maven](./spec2code/java-spring)
  * [Kotlin, Spring Boot, Gradle](./spec2code/kotlin-spring)
* code to spec
  * [springdoc-openapi](./code2spec/springdoc)

### Currrent state

Function | [OG 'spring'](./spec2code/java-spring) | [OG 'kotlin-spring'](./spec2code/kotlin-spring) | [springdoc-openapi](./code2spec/springdoc)
:------------ | :-------------| :-------------| :-------------
simple model | ✅ | ✅ | ✅
simple path | ✅ | ✅ | ✅
enums | ✅ |  | 
inheritance | ✅ | ❌ | 
header | ✅ | | 
references | ✅ | | 
security | | | 