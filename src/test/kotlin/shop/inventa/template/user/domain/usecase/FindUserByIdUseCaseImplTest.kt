package shop.inventa.template.user.domain.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import shop.inventa.template.user.domain.model.UserMother
import shop.inventa.template.user.domain.port.FindUserPort

@ExtendWith(MockKExtension::class)
internal class FindUserByIdUseCaseImplTest {
    @MockK
    private lateinit var findUserPort: FindUserPort

    @InjectMockKs
    private lateinit var findUserByIdUseCase: FindUserByIdUseCaseImpl

    @Test
    fun `should call port correctly`() {
        // given
        val mockUser = UserMother.validUser()
        val searchId = 1L

        every { findUserPort.findById(searchId) } returns mockUser

        // when
        val user = findUserByIdUseCase.execute(FindUserByIdUseCase.FindUserByIdCommand(searchId))

        // then
        assertNotNull(user)
        user!!
        assertAll("user", {
            assertEquals(mockUser.id, user.id)
            assertEquals(mockUser.uuid, user.uuid)
            assertEquals(mockUser.name, user.name)
            assertEquals(mockUser.email, user.email)
        })
        verify(exactly = 1) { findUserPort.findById(searchId) }
    }

    @Test
    fun `should return null if port does not return user`() {
        // given
        val searchId = 1L
        every { findUserPort.findById(searchId) } returns null

        // when
        val user = findUserByIdUseCase.execute(FindUserByIdUseCase.FindUserByIdCommand(searchId))

        // then
        assertNull(user)
        verify(exactly = 1) { findUserPort.findById(searchId) }
    }
}
