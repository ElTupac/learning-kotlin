package shop.inventa.template.user.domain.model

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import shop.inventa.template.user.domain.exception.InvalidEmailException

@ExtendWith(MockKExtension::class)
internal class UserTest {
    @Test
    fun `should pass with valid email`() {
        assertDoesNotThrow { UserMother.validUser() }
    }

    @Test
    fun `should throw exception when email is invalid`() {
        assertThrows<InvalidEmailException> { UserMother.invalidEmailUser() }
    }
}
