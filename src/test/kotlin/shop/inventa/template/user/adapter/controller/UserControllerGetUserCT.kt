package shop.inventa.template.user.adapter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import shop.inventa.template.config.annotation.ControllerIntegrationTest
import shop.inventa.template.user.adapter.dto.UserDto
import shop.inventa.template.user.domain.model.User
import shop.inventa.template.user.domain.model.UserMother
import shop.inventa.template.user.domain.usecase.FindUserByIdUseCase

@ControllerIntegrationTest
class UserControllerGetUserCT {

    val baseUrl = "/v1/users/"
    val userId = "1"
    val completeUrl = baseUrl + userId

    @MockkBean
    private lateinit var findUserByIdUseCase: FindUserByIdUseCase

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `getUser - should return not found when user does not exist`() {
        every { findUserByIdUseCase.execute(any()) } returns null

        mockMvc.perform(
            MockMvcRequestBuilders
                .get(completeUrl)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)

        verify(exactly = 1) { findUserByIdUseCase.execute(any()) }
    }

    @Test
    fun `getUser - should return proper info when user exists`() {
        val user: User = UserMother.validUser()
        val expectedUser: String = objectMapper.writeValueAsString(UserDto(user))

        every { findUserByIdUseCase.execute(any()) } returns user

        val result: String = mockMvc.perform(
            MockMvcRequestBuilders
                .get(completeUrl)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
            .response
            .contentAsString

        assertEquals(expectedUser, result)

        verify(exactly = 1) { findUserByIdUseCase.execute(any()) }
    }
}
