import com.github.gradle.node.npm.task.NpxTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    val kotlinVersion = "2.1.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"

    id("org.openapi.generator") version "7.11.0"
    id("com.github.node-gradle.node") version "7.1.0"

    id("com.github.ben-manes.versions") version "0.52.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
}

group = "de.codecentric"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register<NpxTask>("lintApiSpec") {
    command = "@redocly/cli"
    args = listOf("lint", "./src/main/spec/api-spec.yaml")
}

tasks.register<NpxTask>("buildApiDocs") {
    dependsOn("lintApiSpec")

    command = "@redocly/cli"
    args =
        listOf(
            "build-docs",
            "./src/main/spec/api-spec.yaml",
            "-o",
            "${layout.buildDirectory.get()}/docs/api-spec.html",
        )
}

val generatedOpenApiSourcesDir = "${layout.buildDirectory.get()}/generated-openapi"

tasks.named<GenerateTask>("openApiGenerate") {
    dependsOn("lintApiSpec")
    generatorName.set("kotlin-spring")

    inputSpec.set("src/main/spec/api-spec.yaml")
    outputDir.set(generatedOpenApiSourcesDir)

    configFile.set("src/main/spec/api-config.json")
}

java.sourceSets["main"].java.srcDir(generatedOpenApiSourcesDir)

tasks.withType<KotlinCompile> {
    kotlin {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
        }
    }
    dependsOn(listOf(tasks.openApiGenerate, "buildApiDocs"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    dependencyUpdates {
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected =
                        listOf(
                            "alpha",
                            "beta",
                            "rc",
                            "cr",
                            "m",
                            "preview",
                            "b",
                            "ea",
                        ).map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
                            .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }
                }
            }
        }
    }
}

ktlint {
    version.set("1.5.0")
    filter {
        exclude { entry -> entry.file.toString().contains("generated") }
    }
}

tasks.named("runKtlintCheckOverMainSourceSet").configure { dependsOn("openApiGenerate") }
