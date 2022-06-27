package shop.inventa.template.user.domain.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import shop.inventa.template.user.domain.model.UserMother
import shop.inventa.template.user.domain.port.FindUserPort

@ExtendWith(MockKExtension::class)
internal class FindUserByEmailUseCaseImplTest {
    @MockK
    private lateinit var findUserPort: FindUserPort

    @InjectMockKs
    private lateinit var findUserByEmailUseCase: FindUserByEmailUseCaseImpl

    @Test
    fun `should call port correctly`() {
        // given
        val mockUser = UserMother.validUser()
        val searchEmail = "sample@email.com"

        every { findUserPort.findByEmail(searchEmail) } returns mockUser

        // when
        val user = findUserByEmailUseCase.execute(FindUserByEmailUseCase.FindUserByEmailCommand(searchEmail))

        // then
        Assertions.assertNotNull(user)
        user!!
        assertAll("user", {
            Assertions.assertEquals(mockUser.id, user.id)
            Assertions.assertEquals(mockUser.uuid, user.uuid)
            Assertions.assertEquals(mockUser.name, user.name)
            Assertions.assertEquals(mockUser.email, user.email)
        })
        verify(exactly = 1) { findUserPort.findByEmail(searchEmail) }
    }

    @Test
    fun `should return null if port does not return user`() {
        // given
        val searchEmail = "sample@email.com"
        every { findUserPort.findByEmail(searchEmail) } returns null

        // when
        val user = findUserByEmailUseCase.execute(FindUserByEmailUseCase.FindUserByEmailCommand(searchEmail))

        // then
        Assertions.assertNull(user)
        verify(exactly = 1) { findUserPort.findByEmail(searchEmail) }
    }
}
