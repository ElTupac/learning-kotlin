package shop.inventa.template.user.domain.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import shop.inventa.template.user.domain.model.User
import shop.inventa.template.user.domain.model.UserMother
import shop.inventa.template.user.domain.port.CreateUserPort
import shop.inventa.template.user.domain.port.UserEventPort

@ExtendWith(MockKExtension::class)
internal class CreateUserUseCaseImplTest {
    @MockK
    private lateinit var createUserPort: CreateUserPort

    @MockK
    private lateinit var userEventPort: UserEventPort

    @InjectMockKs
    private lateinit var createUserUseCase: CreateUserUseCaseImpl

    @Test
    fun `should call ports correctly and return created user`() {
        // given
        val mockValidUser = UserMother.validUser()
        val mockUser = User(name = mockValidUser.name, email = mockValidUser.email)
        val userSlot = slot<User>()

        every { createUserPort.create(user = capture(userSlot)) } returns mockValidUser
        every { userEventPort.created(mockValidUser.uuid!!) } returns Unit

        // when
        val createdUser = createUserUseCase.execute(CreateUserUseCase.CreateUserCommand(mockUser))

        // then
        val capturedUser = userSlot.captured
        assertAll("captured user is as expected", {
            assertEquals(mockUser.name, capturedUser.name)
            assertEquals(mockUser.email, capturedUser.email)
            assertNull(capturedUser.id)
            assertNull(capturedUser.id)
        })
        assertAll("returned user is expected", {
            assertEquals(mockValidUser.name, createdUser.name)
            assertEquals(mockValidUser.email, createdUser.email)
            assertEquals(mockValidUser.id, createdUser.id)
            assertEquals(mockValidUser.uuid, createdUser.uuid)
        })
        verify(exactly = 1) { createUserPort.create(capturedUser) }
        verify(exactly = 1) { userEventPort.created(mockValidUser.uuid!!) }
    }
}
