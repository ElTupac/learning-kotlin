package shop.inventa.template.user.adapter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import shop.inventa.template.config.annotation.ControllerIntegrationTest
import shop.inventa.template.user.adapter.dto.UserCreationDto
import shop.inventa.template.user.adapter.dto.UserDto
import shop.inventa.template.user.adapter.dto.mother.UserCreationDtoMother
import shop.inventa.template.user.domain.model.User
import shop.inventa.template.user.domain.model.UserMother
import shop.inventa.template.user.domain.usecase.CreateUserUseCase

@ControllerIntegrationTest
class UserControllerCreateUserCT {

    val baseUrl = "/v1/users/"
    val emptyString = ""

    @MockkBean
    private lateinit var createUserUseCase: CreateUserUseCase

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `createUser - invalid name should fail`() {
        val input = UserCreationDto(emptyString, "email@email.com")

        mockMvc.perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)

        verify(exactly = 0) { createUserUseCase.execute(any()) }
    }

    @Test
    fun `createUser - invalid email should fail`() {
        val input = UserCreationDto("name", "")

        mockMvc.perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)

        verify(exactly = 0) { createUserUseCase.execute(any()) }
    }

    @Test
    fun `createUser - user creation succeeded`() {
        val input: UserCreationDto = UserCreationDtoMother.getValidDto()
        val createdUser: User = UserMother.validUser()
        val expectedOutput: String = objectMapper.writeValueAsString(UserDto(createdUser))

        every { createUserUseCase.execute(any()) } returns createdUser

        val result: String = mockMvc.perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()
            .response
            .contentAsString

        assertEquals(expectedOutput, result)

        verify(exactly = 1) { createUserUseCase.execute(any()) }
    }
}
