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
import java.util.UUID

@ExtendWith(MockKExtension::class)
internal class FindUserByUUIDUseCaseImplTest {
    @MockK
    private lateinit var findUserPort: FindUserPort

    @InjectMockKs
    private lateinit var findUserByUUIDUseCase: FindUserByUUIDUseCaseImpl

    @Test
    fun `should call port correctly`() {
        // given
        val mockUser = UserMother.validUser()
        val searchUUID = mockUser.uuid!!

        every { findUserPort.findUserByUUID(searchUUID) } returns mockUser

        // when
        val user = findUserByUUIDUseCase.execute(FindUserByUUIDUseCase.FindUserByUUIDCommand(searchUUID))

        // then
        Assertions.assertNotNull(user)
        user!!
        assertAll("user", {
            Assertions.assertEquals(mockUser.id, user.id)
            Assertions.assertEquals(mockUser.uuid, user.uuid)
            Assertions.assertEquals(mockUser.name, user.name)
            Assertions.assertEquals(mockUser.email, user.email)
        })
        verify(exactly = 1) { findUserPort.findUserByUUID(searchUUID) }
    }

    @Test
    fun `should return null if port does not return user`() {
        // given
        val searchUUID = UUID.randomUUID()
        every { findUserPort.findUserByUUID(searchUUID) } returns null

        // when
        val user = findUserByUUIDUseCase.execute(FindUserByUUIDUseCase.FindUserByUUIDCommand(searchUUID))

        // then
        Assertions.assertNull(user)
        verify(exactly = 1) { findUserPort.findUserByUUID(searchUUID) }
    }
}
