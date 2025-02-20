package de.codecentric.kotlinspring.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AuthorServiceTest {
    private var cut = AuthorService()
    private val testObject = Author(firstName = "John", lastName = "Doe")

    @Test
    fun `should create author`() {
        cut.save(testObject)

        assertThat(cut.get(testObject.id)).isNotNull
        assertThat(cut.list(null)).isNotEmpty
    }

    @Test
    fun `should update author`() {
        cut.save(testObject)

        val updatedAuthor = testObject.copy(firstName = "Jane", lastName = "Roe")
        cut.save(updatedAuthor)

        assertThat(cut.get(testObject.id).firstName).isEqualTo("Jane")
    }

    @Test
    fun `should find author`() {
        cut.save(testObject)

        assertThat(cut.list("John")).isNotEmpty
    }

    @Test
    fun `should delete author`() {
        cut.save(testObject)

        cut.delete(testObject.id)

        assertThat(cut.list(null)).isEmpty()
    }

    @Test
    fun `should fail if author was deleted twice`() {
        cut.save(testObject)

        cut.delete(testObject.id) // 1st deletion

        assertThrows<NotFoundException> {
            cut.delete(testObject.id) // 2nd deletion
        }
    }
}
