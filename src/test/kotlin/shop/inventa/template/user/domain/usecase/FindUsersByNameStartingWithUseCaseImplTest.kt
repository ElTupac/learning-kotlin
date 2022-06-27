package shop.inventa.template.user.domain.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import shop.inventa.template.user.domain.model.UserMother
import shop.inventa.template.user.domain.port.FindUserPort

@ExtendWith(MockKExtension::class)
internal class FindUsersByNameStartingWithUseCaseImplTest {
    @MockK
    private lateinit var findUserPort: FindUserPort

    @InjectMockKs
    private lateinit var findUsersByNameStartingWithUseCase: FindUsersByNameStartingWithUseCaseImpl

    @Test
    fun `should call port correctly`() {
        // given
        val mockUser = UserMother.validUser()
        val searchPartialName = "Sam"

        every { findUserPort.findByNameStartsWith(searchPartialName) } returns listOf(mockUser)

        // when
        val users = findUsersByNameStartingWithUseCase.execute(
            FindUsersByNameStartingWithUseCase
                .FindUsersByNameStartingWithCommand(searchPartialName)
        )

        // then
        assertTrue(users.isNotEmpty())
        val user = users[0]
        assertAll("user", {
            assertEquals(mockUser.id, user.id)
            assertEquals(mockUser.uuid, user.uuid)
            assertEquals(mockUser.name, user.name)
            assertEquals(mockUser.email, user.email)
        })
        verify(exactly = 1) { findUserPort.findByNameStartsWith(searchPartialName) }
    }

    @Test
    fun `should return empty list if port does not return any user`() {
        // given
        val searchPartialName = "Sam"
        every { findUserPort.findByNameStartsWith(searchPartialName) } returns listOf()

        // when
        val users = findUsersByNameStartingWithUseCase.execute(
            FindUsersByNameStartingWithUseCase
                .FindUsersByNameStartingWithCommand(searchPartialName)
        )

        // then
        assertTrue(users.isEmpty())
        verify(exactly = 1) { findUserPort.findByNameStartsWith(searchPartialName) }
    }
}
